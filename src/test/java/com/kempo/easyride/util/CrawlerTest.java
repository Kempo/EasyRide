package com.kempo.easyride.util;

import com.google.api.services.sheets.v4.Sheets;
import com.kempo.easyride.google.SheetsAPI;
import junit.framework.TestCase;

public class CrawlerTest extends TestCase {

    public void testCrawler() throws Exception {
        final String sheetsURL = "https://docs.google.com/spreadsheets/d/17jczuEenPqXwJtCma3km1IW452no54ECNI_1DfCv9_4/edit#gid=0";
        final Sheets service = SheetsAPI.getSheetsService();
        final Crawler crawler = new Crawler(service, SheetsAPI.getIDFromURL(sheetsURL));
        System.out.println(crawler.getNameColumn() + " " + crawler.getAddressColumn() + " " + crawler.getDesignationColumn());
    }

    /*

    just a lengthy test

    private RideParser parser = new RideParser();
    private Orchestrator orchestrator = new Orchestrator(new RideAssigner());

    public void testReadAndAssignSheets() throws Exception {
        String dataRange = "A2:D16";
        String sheetsID = SheetsAPI.getIDFromURL("https://docs.google.com/spreadsheets/d/1wQPfybTVK-d_2F4ArVD6I_IRK7iNgY1L1rDm5PM-HVw/edit?ouid=101645746693707733801&usp=sheets_home&ths=true");
        Sheets service = SheetsAPI.getSheetsService();
        ValueRange values = service.spreadsheets().values().get(sheetsID, dataRange).setKey(SheetsAPI.API_KEY).execute();

        final RawParticipants participants = parser.parseInitialRequestThroughSheets(values, null);
        final AssignedRides result = orchestrator.orchestrateRides(participants);

        for(Driver d : result.getDrivers()) {
            System.out.println("NAME: " + d.getName());
            for(Rider r : d.getCar().getOccupants()) {
                System.out.println(r.getName());
            }
            System.out.println();
        }

        System.out.println(result.getUnassignedOrUnparseable());
        Assert.assertEquals(true, true);
    }
     */
}
