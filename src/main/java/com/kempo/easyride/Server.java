package com.kempo.easyride;

import com.kempo.easyride.application.Orchestrator;
import com.kempo.easyride.application.RideAssigner;
import com.kempo.easyride.util.RideParser;
import com.kempo.easyride.google.SheetsAPI;
import com.kempo.easyride.util.ServerHelper;
import org.apache.log4j.BasicConfigurator;

import java.nio.charset.StandardCharsets;

import static spark.Spark.*;

public class Server {

    private static RideParser parser = new RideParser();
    private static Orchestrator orchestrator = new Orchestrator(new RideAssigner());

    public static void main(String[] args)
    {
        // TODO: specify certain logging settings
        BasicConfigurator.configure();
        port(getHerokuAssignedPort());
        staticFileLocation("/public");
        // TODO: specify CORS policy for security
        enableCORS("*", "GET, POST", "*");

        exception(Exception.class, (exception, request, response) -> {
            exception.printStackTrace();
        });

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

    // Enables CORS on requests. This method is an initialization method and should be called once.
    private static void enableCORS(final String origin, final String methods, final String headers) {

        options("/*", (request, response) -> {

            String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }

            String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }

            return "OK";
        });

        before((request, response) -> {
            response.header("Access-Control-Allow-Origin", origin);
            response.header("Access-Control-Request-Method", methods);
            response.header("Access-Control-Allow-Headers", headers);
            // Note: this may or may not be necessary in your particular application
            // response.type("application/json");
        });
    }

}
