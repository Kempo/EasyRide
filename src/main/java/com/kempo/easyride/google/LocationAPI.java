package com.kempo.easyride.google;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class LocationAPI {
    private static final String LOCATION_KEY = System.getenv("LOCATION_KEY");

    private static final String DELIMITER = "%20";

    /** deprecated. this is a very wrong way of handling JSON parsing
     *
    public static boolean isLocationValid(String address) {

        final String formatted = address.replaceAll(" ", DELIMITER);
        final String link = "https://maps.googleapis.com/maps/api/geocode/json?address=" + formatted + "&key=" + API_KEY;
        try {
            URL url = new URL(link);
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;
            while((line = reader.readLine()) != null) {
                if(line.contains("\"status\" : \"ZERO_RESULTS\"")) {
                    return false;
                }

            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        return true;
    }
    **/

    public static String getFormattedAddress(String address) {

        final String formatted = address.replaceAll(" ", DELIMITER);
        final String link = "https://maps.googleapis.com/maps/api/geocode/json?address=" + formatted + "&key=" + LOCATION_KEY;
        try {
            URL url = new URL(link);
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            JSONObject obj = new JSONObject(response.toString());
            JSONArray results = obj.getJSONArray("results");
            if(results.isNull(0)) { // if the results are not valid
                return null;
            }else {
                if (!results.getJSONObject(0).isNull("formatted_address")) { // checks if 'formatted_address' is null
                    String a = results.getJSONObject(0).get("formatted_address").toString(); // stores data
                    return a;
                }
            }

        }catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }



}
