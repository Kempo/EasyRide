package com.kempo.easyride.google;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.time.temporal.ChronoUnit.SECONDS;

// DISTANCE API
public class MapsAPI {
    private static final String MAPS_KEY = System.getenv("MAPS_KEY"); // Google Distance Matrix API key

    private static final String DELIMITER = "%20";

    public static double fetchDistance(String origin, String destination) {

        try {
            final HttpRequest req = HttpRequest.newBuilder()
                    .uri(new URI("https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&origins=" + format(origin) + "&destinations=" + format(destination) + "&key=" + MAPS_KEY))
                    .timeout(Duration.of(10, SECONDS))
                    .GET()
                    .build();

            final HttpResponse<String> response = HttpClient
                    .newBuilder()
                    .build()
                    .send(req, HttpResponse.BodyHandlers.ofString());

            final JSONObject body = new JSONObject(response.body());
            String distance = body.getJSONArray("rows").getJSONObject(0)
                    .getJSONArray("elements").getJSONObject(0).getJSONObject("distance").get("text").toString();
            System.out.println(distance);

            return parseDouble(distance);

        }catch(Exception e) {
            e.printStackTrace();
        }

        return -1;
    }

    private static double parseDouble(String s) {
        s = s.replaceAll(",", "");
        if(s.contains("mi")) {
            s = s.replace("mi", "");
            Double d = Double.parseDouble(s);
            return d; // default measuring unit is miles
        }else if (s.contains("ft")){
            s = s.replace("ft", "");
            Double d = Double.parseDouble(s);
            return (d / 5280); // if the calculations are in feet, we want to return it in form of miles
        }
        return -1;
    }


    private static String format(String s) {
        return s.replaceAll(" ",DELIMITER);
    }

}
