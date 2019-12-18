package com.kempo.easyride.util;

import com.kempo.easyride.google.LocationAPI;
import com.kempo.easyride.google.MapsAPI;
import com.kempo.easyride.google.SheetsAPI;
import junit.framework.Assert;
import junit.framework.TestCase;

import javax.xml.stream.Location;
import java.io.IOException;
import java.net.URISyntaxException;

public class MiscTest extends TestCase {

    public void testURLParser() {
        String URL = "https://docs.google.com/spreadsheets/d/sheetsID/edit#gid=0";
        String ID = SheetsAPI.getIDFromURL(URL);
        Assert.assertEquals("sheetsID", ID);
    }

    /**
     * tests long distance travel
     */
    public void testLongDistanceWithAPI() {
        String origin = "New York";
        String destination = "Seattle";
        double dist = MapsAPI.getDistance(origin, destination);
        Assert.assertEquals(2861.0, dist);
    }

    /**
     * tests to see if distances less than a mile are valid
     */
    public void testShortDistancWithAPI() {
        String origin = "3040 NE 45th St, Seattle, WA 98105";
        String destination = "3042 NE 45th St, Seattle, WA 98105";
        double dist = MapsAPI.getDistance(origin, destination);
        Assert.assertEquals(true, (dist < .5)); // distances between the places should be less than half a mile
    }

    /**
     * tests to see if addresses are reformatted if they don't given enough information
     */
    public void testFormattedAddress() throws InterruptedException, IOException, URISyntaxException {
        String f = LocationAPI.fetchFormatted("5041 35th Ave NE");
        System.out.println(f);
        Assert.assertEquals(true, f.contains("Seattle")); // "Seattle" will be part of the entire address after formatting
    }
}
