package com.kempo.easyride.google;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class LocationAPI {
    private static final String API_KEY = "AIzaSyDeYYFr4IqU9nAvjIzM5NRvWduEkSUEaro";
    private static final String DELIMITER = "%20";

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
}
