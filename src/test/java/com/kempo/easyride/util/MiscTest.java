package com.kempo.easyride.util;

import com.kempo.easyride.google.MapsAPI;
import com.kempo.easyride.google.SheetsAPI;
import junit.framework.Assert;
import junit.framework.TestCase;

public class MiscTest extends TestCase {

    private MapsAPI maps = new MapsAPI();
    public void testURLParser() {
        String URL = "https://docs.google.com/spreadsheets/d/sheetsID/edit#gid=0";
        String ID = SheetsAPI.getIDFromURL(URL);
        Assert.assertEquals("sheetsID", ID);
    }

    public void testLongDistanceWithAPI() {
        String origin = "New York";
        String destination = "Seattle";
        double dist = maps.getDistance(origin, destination);
        Assert.assertEquals(2852.0, dist);
    }
    public void testShortDistancWithAPI() {
        String origin = "3040 NE 45th St, Seattle, WA 98105";
        String destination = "3042 NE 45th St, Seattle, WA 98105";
        double dist = MapsAPI.getDistance(origin, destination);
        Assert.assertEquals(true, (dist < .5)); // distances between the places should be less than half a mile
    }
}
