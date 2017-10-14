package com.kempo.easyride;

import com.kempo.easyride.model.RawParticipants;
import com.kempo.easyride.util.RideParser;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.staticFileLocation;

public class Server {

    private static RideParser parser = new RideParser();

    public static void main(String[] args)
    {
        port(getHerokuAssignedPort());
        staticFileLocation("/public");
        get("/ping", (req, res) -> "pong");
        post("/rides", (req, res) -> {
            System.out.println("parsing...");
            final RawParticipants participants = parser.parseInitialRequest(req.body());
            System.out.println(participants);
            return participants.toString();
        });
    }

    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }
}
