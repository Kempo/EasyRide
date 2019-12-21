package com.kempo.easyride;

import com.kempo.easyride.application.Orchestrator;
import com.kempo.easyride.application.RideAssigner;
import com.kempo.easyride.util.RideParser;
import com.kempo.easyride.google.SheetsAPI;
import com.kempo.easyride.util.ServerHelper;

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
            return ServerHelper.getDataThroughSheets(SheetsAPI.getIDFromURL(url), dataRange, parser, orchestrator);
        });

        post("/rides", (req, res) -> {
            String request = new String(req.bodyAsBytes(), StandardCharsets.UTF_8);
            request = request.replaceAll("\r\n","\n"); // to remove CRLF line terminators
            return ServerHelper.getDataThroughTSV(request, parser, orchestrator);
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
