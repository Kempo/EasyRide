package com.kempo.easyride.util;


import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.kempo.easyride.application.Orchestrator;
import com.kempo.easyride.google.SheetsAPI;
import com.kempo.easyride.model.AssignedRides;
import com.kempo.easyride.model.RawParticipants;

public class ServerHelper {

    public static String getDataThroughSheets(final String sheetsID, final String dataRange, final RideParser parser, final Orchestrator orchestrator) throws Exception {
        Sheets service = SheetsAPI.getSheetsService();
        ValueRange values = service.spreadsheets().values().get(sheetsID, dataRange).setKey(SheetsAPI.API_KEY).execute();
        System.out.println("SERVICE: " + service.getApplicationName() + " STATUS: PARSING...");
        final RawParticipants participants = parser.parseInitialRequestThroughSheets(values);
        final AssignedRides result = orchestrator.orchestrateRides(participants);
        System.out.println("REQUEST COMPLETE.");
        return result.toString();
    }

    public static String getDataThroughTSV(final String request, final RideParser parser, final Orchestrator orchestrator) {
        final RawParticipants participants = parser.parseInitialRequestThroughTSV(request);
        final AssignedRides result = orchestrator.orchestrateRides(participants);
        System.out.println("REQUEST COMPLETE.");
        return result.toString();
    }
}
