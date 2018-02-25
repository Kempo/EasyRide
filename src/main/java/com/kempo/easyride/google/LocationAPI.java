package com.kempo.easyride.google;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.xml.bind.annotation.XmlType;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class LocationAPI {
    private static final String LOCATION_KEY = System.getenv("LOCATION_KEY");

    private static final String DELIMITER = "%20";

    private static final String[] DEFAULT_STATE = {"WASHINGTON", "washington", "wa", "WA"};

    private static final ArrayList<String> STATE_LIST = new ArrayList<>();

    static {
        STATE_LIST.addAll(Arrays.asList(DEFAULT_STATE));
    }

    public static String getFormattedAddress(String unformatted) {
        StringBuilder address = new StringBuilder(unformatted);
        if(!isStateDeclared(unformatted)) {
            address.append(" " + DEFAULT_STATE[0]);
        }
        final String formatted = address.toString().replaceAll(" ", DELIMITER);

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

    private static boolean isStateDeclared(String address) {
        for(String state : STATE_LIST) {
            if (address.contains(state)) {
                return true;
            }
        }
        return false;
    }

    public static void addDefaultState(String[] abbreviations) {
        STATE_LIST.addAll(Arrays.asList(abbreviations));
    }
}
