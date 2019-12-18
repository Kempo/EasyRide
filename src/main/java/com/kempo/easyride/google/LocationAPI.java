package com.kempo.easyride.google;

import com.kempo.easyride.util.Keywords;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;

import static java.time.temporal.ChronoUnit.SECONDS;

// GEOCODE
public class LocationAPI {
    private static final String LOCATION_KEY = System.getenv("LOCATION_KEY");

    private static final String DELIMITER = "%20";

    private static final ArrayList<String> STATE_LIST = new ArrayList<>();

    static {
        STATE_LIST.addAll(Arrays.asList(Keywords.DEFAULT_STATE)); // adds all the keywords to the list when the class is loaded in memory
    }

    public static String fetchFormatted(String original) {

        if(!isStateDeclared(original)) {
            original += Keywords.DEFAULT_STATE[0];
        }
        try {

            final HttpRequest req = HttpRequest.newBuilder()
                    .uri(new URI("https://maps.googleapis.com/maps/api/geocode/json?address=" + original.replace(" ", DELIMITER) + "&key=" + LOCATION_KEY))
                    .timeout(Duration.of(10, SECONDS))
                    .GET()
                    .build();

            final HttpResponse<String> response = HttpClient
                    .newBuilder()
                    .build()
                    .send(req, HttpResponse.BodyHandlers.ofString());

            final JSONObject body = new JSONObject(response.body());
            final JSONArray results = body.getJSONArray("results");

            if(results.isNull(0) || results.getJSONObject(0).isNull("formatted_address")) {
                return null;
            }

            final String formatted = results.getJSONObject(0).get("formatted_address").toString();

            return formatted;

        }catch(Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private static boolean isStateDeclared(String address) {
        for(String state : STATE_LIST) {
            if (address.toLowerCase().contains(state)) {
                return true;
            }
        }
        return false;
    }

    public static void addDefaultState(String[] abbreviations) {
        STATE_LIST.addAll(Arrays.asList(abbreviations));
    }
}
