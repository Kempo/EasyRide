package com.kempo.easyride.google;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;

import java.io.IOException;

public class SheetsAPI {

    private static final String APPLICATION_NAME = "EasyRide";

    public static final String API_KEY = System.getenv("SHEETS_KEY");

    private static final JsonFactory JSON_FACTORY =
            JacksonFactory.getDefaultInstance();

    private static HttpTransport HTTP_TRANSPORT;

    /**
     * Build and return an authorized Sheets API client service.
     * @return an authorized Sheets API client service
     * @throws IOException
     */
    public static Sheets getSheetsService() throws IOException {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        } catch (Throwable t) {
            t.printStackTrace();
            System.out.println("error with HTTP_TRANSPORT");
            System.exit(1);
        }

        Credential credential = null;
        return new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();

    }

    /**
     * Parses out the Google Sheets ID from a URL
     * @param url
     * @return ID
     */
    public static String getIDFromURL(String url) {
        String sheetID = "";
        try {
            if (url != null && url.contains("http://")) {
                url = url.replaceAll("http://", "");
            }

            String[] tokens = url.split("/");
            int i = 0;
            for (String s : tokens) {
                if (s.equals("d")) { // signifier that the next token will be the ID
                    sheetID = tokens[i + 1];
                }
                i += 1;
            }
        }catch(Exception e) {
            e.printStackTrace();
            sheetID = "no ID found";
            return sheetID;
        }
        return sheetID;
    }
}
