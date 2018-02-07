package com.kempo.easyride.google;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MapsAPI {
    private static final String MAPS_KEY = System.getenv("MAPS_KEY"); // Google Distance Matrix API key

    private static final String DELIMITER = "%20";


    /**
     * deprecated. very poor way of handling JSON parsing once again
     * @param o
     * @param d
     * @return distance in kilometers

    public double getDistance(String o, String d) {
        String link = "https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&origins=" + setOrigin(o) + "&destinations=" + setDestination(d) + "&key=" + KEY;
        String distance = "";
        try {
            URL url = new URL(link);

            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream())); // creates a BufferedReader that can read our URL

            String line;
            while((line = reader.readLine()) != null) {
                System.out.println(line);
                if(line.contains("\"distance\"")) { // if the current line contains "distance"; this is where we parse the lines on Google's response
                    distance = reader.readLine(); // sets our variable to the next line (where the distance in kilometers will be displayed)
                }
            }
        }catch(Exception e) {
            e.printStackTrace();
        }

        if(distance.isEmpty() || distance.contains("[]")) { // if our string is empty or if it contains [] then it's invalid and should'nt be parsed
            System.out.println("invalid distance string= '" + distance + "'");
            return -1;
        }

        double dist = parseDouble(distance); // parses the distance string

        if(dist < 0) { // if it is less than 0, meaning an unsuccessful and invalid parse
            System.out.println("unsuccessful parse");
        }

        return dist;
    }
    */

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
