package com.kempo.easyride.util;

import com.google.gson.Gson;
import com.kempo.easyride.google.LocationAPI;
import com.kempo.easyride.google.MapsAPI;
import com.kempo.easyride.google.SheetsAPI;
import com.kempo.easyride.model.Car;
import com.kempo.easyride.model.Driver;
import com.kempo.easyride.model.Rider;
import junit.framework.Assert;
import junit.framework.TestCase;

import javax.xml.stream.Location;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

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
        double dist = MapsAPI.fetchDistance(origin, destination);
        Assert.assertEquals(true, (dist <= 2862 && dist >= 2861));
    }

    /**
     * tests to see if distances less than a mile are valid
     */
    public void testShortDistancWithAPI() {
        String origin = "3040 NE 45th St, Seattle, WA 98105";
        String destination = "3042 NE 45th St, Seattle, WA 98105";
        double dist = MapsAPI.fetchDistance(origin, destination);
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

    /**
     * tests to see if addresses are reformatted if they don't given enough information
     */
    public void testFormattedAddress2() throws InterruptedException, IOException, URISyntaxException {
        String f = LocationAPI.fetchFormatted("5041 35th Ave NE");
        System.out.println(f);
        Assert.assertEquals(true, f.contains("Seattle")); // "Seattle" will be part of the entire address after formatting
    }

    public void testJSONOutput() {
        ArrayList<Driver> drivers = new ArrayList<>();
        drivers.add(new Driver("Charles", "New York", new Car(4)));
        drivers.get(0).getCar().addOccupant(new Rider("Aaron", "New York"));
        drivers.add(new Driver("Nick", "Texas", new Car(2)));
        drivers.add(new Driver("Tim", "North Dakota", new Car(3)));
        System.out.println(new Gson().toJson(drivers));

    }
}
