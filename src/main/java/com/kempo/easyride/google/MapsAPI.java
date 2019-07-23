package com.kempo.easyride.google;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
// DISTANCE API
public class MapsAPI {
    private static final String MAPS_KEY = System.getenv("MAPS_KEY"); // Google Distance Matrix API key

    private static final String DELIMITER = "%20";

    /**
     * @param o
     * @param d
     * @return distance in kilometers
     */
    public static double getDistance(String o, String d) {
        String link = "https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&origins=" + format(o) + "&destinations=" + format(d) + "&key=" + MAPS_KEY;
        try {
            URL url = new URL(link);

            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream())); // creates a BufferedReader that can read our URL
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            JSONObject obj = new JSONObject(response.toString());
            JSONArray rows = obj.getJSONArray("rows");
            JSONObject element = rows.getJSONObject(0);
            String distString = element.getJSONArray("elements").getJSONObject(0).getJSONObject("distance").get("text").toString();
            double distance = parseDouble(distString);
            return distance;

        } catch (Exception e) {
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
