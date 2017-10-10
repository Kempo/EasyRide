package com.kempo.easyride;
import static spark.Spark.*;

public class Server {
    public static void main(String[] args)
    {
        staticFileLocation("/public");
        get("/ping", (req, res) -> "pong");

    }
}
