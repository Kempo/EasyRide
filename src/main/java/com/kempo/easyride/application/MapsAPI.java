package com.kempo.easyride.application;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MapsAPI {
    private final String KEY = "AIzaSyDjkGSDw_dX7iJhvb5mHu8rotwB0WfJgjk"; // Google Distance Matrix API key
    /**
     *
     * @param o
     * @param d
     * @return distance in kilometers
     */
    public double getDistance(String o, String d) {
        String link = "https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&origins=" + setOrigin(o) + "&destinations=" + setDestination(d) + "&key=" + KEY;
        String distance = "";
        try {
            URL url = new URL(link);

            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream())); // creates a BufferedReader that can read our URL

            String line;
            while((line = reader.readLine()) != null) {
                if(line.contains("\"distance\"")) { // if the current line contains "distance"; this is where we parse the lines on Google's response
                    distance = reader.readLine(); // sets our variable to the next line (where the distance in kilometers will be displayed)
                }
            }
        }catch(Exception e) {
            e.printStackTrace();
        }


        if(!distance.isEmpty() && !distance.contains("[]")) { // if we have found our line containing the distance
            return parseDouble(distance); // returns the value using our parseDouble method
        }

        return -1; // if no distance value is found
    }

    /**
     *
     * @param s
     * @return a specific double (our distance) from a string
     * credits to 'Bohemian'
     */
    public double parseDouble(String s) {
        s = s.replaceAll("\"","");
        Scanner st = new Scanner(s);
        while (!st.hasNextDouble())
        {
            st.next();
        }
        double value = st.nextDouble();
        return value;
        //return -1; // if no double is found
    }


    private String setOrigin(String s) { //example 'University of Washington' to 'University_Of_Washington'
        return s.replaceAll(" ","%20");
    }

    private String setDestination(String s) {
        return s.replaceAll(" ", "%20");
    }

}
