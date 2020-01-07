package com.kempo.easyride.util;

import com.google.api.services.sheets.v4.Sheets;
import com.kempo.easyride.application.Orchestrator;
import com.kempo.easyride.application.RideAssigner;
import com.kempo.easyride.google.SheetsAPI;
import com.kempo.easyride.model.AssignedRides;
import com.kempo.easyride.model.RawParticipants;
import junit.framework.Assert;
import junit.framework.TestCase;

public class CrawlerTest extends TestCase {

    private final RideParser parser = new RideParser();
    private final Orchestrator orchestrator = new Orchestrator(new RideAssigner());
    private final String exampleURL = "https://docs.google.com/spreadsheets/d/17jczuEenPqXwJtCma3km1IW452no54ECNI_1DfCv9_4/edit#gid=0";


    /*
    public void testCrawler() throws Exception {
        final Sheets service = SheetsAPI.getSheetsService();
        final Crawler crawler = new Crawler(service, SheetsAPI.getIDFromURL(sheetsURL));
        System.out.println(crawler.getNameColumn() + " " + crawler.getAddressColumn() + " " + crawler.getDesignationColumn());
    }
     */

    public void testFullCrawler() throws Exception {
        final Sheets service = SheetsAPI.getSheetsService();
        final Crawler crawler = new Crawler(service, SheetsAPI.getIDFromURL(exampleURL));

        //System.out.println(crawler.getNameColumn() + " " + crawler.getAddressColumn() + " " + crawler.getDesignationColumn());
        //System.out.println(crawler.getValueRange().getValues().get(1).get(4));

        final RawParticipants participants = parser.parseInitialRequestThroughCrawler(crawler);
        final AssignedRides result = orchestrator.orchestrateRides(participants);
        Assert.assertEquals((participants.getDrivers().size() + participants.getRiders().size()), 4);

        Assert.assertEquals(result.getDrivers().size(), 2);
        Assert.assertEquals(result.getDrivers().get(0).getCar().getOccupants().size(), 1);

    }
}
