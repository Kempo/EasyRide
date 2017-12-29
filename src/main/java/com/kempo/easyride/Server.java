package com.kempo.easyride;

import com.kempo.easyride.application.Orchestrator;
import com.kempo.easyride.application.RideAssigner;
import com.kempo.easyride.model.AssignedRides;
import com.kempo.easyride.model.RawParticipants;
import com.kempo.easyride.util.RideParser;

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
        get("/ping", (req, res) -> "pong");
        post("/rides", (req, res) -> {
            System.out.println("parsing...");
            String request = new String(req.bodyAsBytes(), StandardCharsets.UTF_8);
            request = request.replaceAll("\r\n","\n"); 
            //request = request.trim();
            System.out.println("request: \n" + request);
            final RawParticipants participants = parser.parseInitialRequest(request);
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
