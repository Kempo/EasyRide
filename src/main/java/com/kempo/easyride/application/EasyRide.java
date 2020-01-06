package com.kempo.easyride.application;


import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.kempo.easyride.application.Orchestrator;
import com.kempo.easyride.google.SheetsAPI;
import com.kempo.easyride.model.AssignedRides;
import com.kempo.easyride.model.RawParticipants;
import com.kempo.easyride.util.Crawler;
import com.kempo.easyride.util.RideParser;

public class EasyRide {

    public static final String getDataThroughSheets(final String sheetsID, final String dataRange, final RideParser parser, final Orchestrator orchestrator) throws Exception {
        final Sheets service = SheetsAPI.getSheetsService();
        final ValueRange values = service.spreadsheets().values().get(sheetsID, dataRange).setKey(SheetsAPI.API_KEY).execute();

        // TODO: factor our redundant code. edit
        String output;
        if(dataRange.isEmpty() || values == null) {
            final Crawler crawler = new Crawler(service, sheetsID);
            final RawParticipants participants = parser.parseInitialRequestThroughCrawler(crawler);
            final AssignedRides result = orchestrator.orchestrateRides(participants);
            output = result.toString();
            System.out.println("REQUEST COMPLETE");
        }else{
            final RawParticipants participants = parser.parseInitialRequestThroughSheets(values, null);
            final AssignedRides result = orchestrator.orchestrateRides(participants);
            output = result.toString();
            System.out.println("REQUEST COMPLETE.");
        }

        return output;
    }

    public static final String getDataThroughTSV(final String request, final RideParser parser, final Orchestrator orchestrator) {
        final RawParticipants participants = parser.parseInitialRequestThroughTSV(request);
        final AssignedRides result = orchestrator.orchestrateRides(participants);
        System.out.println("REQUEST COMPLETE.");
        return result.outputJSON(); // test
    }
}
