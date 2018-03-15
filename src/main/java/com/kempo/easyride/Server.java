package com.kempo.easyride;


import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.kempo.easyride.application.Orchestrator;
import com.kempo.easyride.application.RideAssigner;
import com.kempo.easyride.model.AssignedRides;
import com.kempo.easyride.model.RawParticipants;
import com.kempo.easyride.util.RideParser;
import com.kempo.easyride.google.SheetsAPI;

import java.nio.charset.StandardCharsets;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.staticFileLocation;

public class Server {

    private static RideParser parser = new RideParser();
    private static Orchestrator orchestrator = new Orchestrator(new RideAssigner());

    public static void main(String[] args)
    {
        port(getHerokuAssignedPort());
        staticFileLocation("/public");
        get("/ping", (req, res) -> {
            System.out.println("hello!");
            return "pong";
        });

        post("/sheets", (req, res) -> {
            String url = req.queryParams("sheetsURL");
            String dataRange = req.queryParams("sheetsRange");
            String sheetsID = SheetsAPI.getIDFromURL(url);

            System.out.println("sheetURL: " + url);
            System.out.println("sheetID: " + sheetsID);
            System.out.println("dataRange: " + dataRange);
            Sheets service = SheetsAPI.getSheetsService();

            System.out.println("google sheets service initialized: " + service.getApplicationName());
            ValueRange values = service.spreadsheets().values().get(sheetsID, dataRange).setKey(SheetsAPI.API_KEY).execute();
            System.out.println("parsing...");
            final RawParticipants participants = parser.parseInitialRequestThroughSheets(values);
            final AssignedRides result = orchestrator.orchestrateRides(participants);
            System.out.println("request complete.");
            return result.toString();
        });

        post("/rides", (req, res) -> {
            System.out.println("parsing...");
            String request = new String(req.bodyAsBytes(), StandardCharsets.UTF_8);
            request = request.replaceAll("\r\n","\n"); // to remove CRLF line terminators
            System.out.println("request: \n" + request);
            final RawParticipants participants = parser.parseInitialRequestThroughTSV(request);
            final AssignedRides result = orchestrator.orchestrateRides(participants);
            System.out.println(participants);
            return result.toString();
        });
    }

    private static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }

}
