package com.kempo.easyride.util;

import com.kempo.easyride.google.LocationAPI;
import com.kempo.easyride.model.RawParticipants;
import junit.framework.Assert;
import junit.framework.TestCase;

import java.util.*;

/**
 * Created by dileng on 10/12/17.
 */
public class RideParserTest extends TestCase
{
    private RideParser parser = new RideParser();

    public void testEntryUnparsedWhenCannotParseLabelColumn()
    {
        final String input = "Bilbo Baggins\tNew York\thobbit" +
                "\nGandalf\tSeattle, Washington\tdriver\t2";
        final RawParticipants result = parser.parseInitialRequestThroughTSV(input);
        Assert.assertEquals(1, result.getDrivers().size());
        Assert.assertEquals("Gandalf", result.getDrivers().get(0).getName());
        Assert.assertEquals("Seattle, WA, USA", result.getDrivers().get(0).getAddress());
        Assert.assertEquals(2, result.getDrivers().get(0).getSpaces());
        Assert.assertEquals(1, result.getUnclassifieds().size());
        Assert.assertEquals("Bilbo Baggins\tNew York\thobbit", result.getUnclassifieds().get(0).getLine());
    }

    public void testDriverWhenNoSpacesListedIsUnclassified()
    {
        final String input = "Bilbo\tRedmond, Washington\tdriver";
        final RawParticipants result = parser.parseInitialRequestThroughTSV(input);
        Assert.assertEquals(1, result.getUnclassifieds().size());
        Assert.assertEquals(input, result.getUnclassifieds().get(0).getLine());
    }

    public void testDriverWhenSpacesNotANumberIsUnclassified()
    {
        final String input = "Bilbo\tSan Jose\tdriver\tnan";
        final RawParticipants result = parser.parseInitialRequestThroughTSV(input);
        Assert.assertEquals(1, result.getUnclassifieds().size());
        Assert.assertEquals(input, result.getUnclassifieds().get(0).getLine());
    }

    public void testDriverInputHappyPath()
    {
        final String input = "Bilbo\tSan Jose\tdriver\t5";
        final RawParticipants result = parser.parseInitialRequestThroughTSV(input);
        Assert.assertEquals(0, result.getUnclassifieds().size());
        Assert.assertEquals(1, result.getDrivers().size());
        Assert.assertEquals(5, result.getDrivers().get(0).getSpaces());
    }

    public void testRider()
    {
        final String input = "Bilbo\tBothell, Washington\trider";
        final RawParticipants result = parser.parseInitialRequestThroughTSV(input);
        Assert.assertEquals(0, result.getUnclassifieds().size());
        Assert.assertEquals(0, result.getDrivers().size());
        Assert.assertEquals(1, result.getRiders().size());
        Assert.assertEquals("Bilbo", result.getRiders().get(0).getName());
        Assert.assertEquals("Bothell, WA, USA", result.getRiders().get(0).getAddress());
    }

    /**
     * tests multiple drivers with different addresses nationwide. Technically, these addresses will be appended with 'washington'
     * since they don't specify the default state. although the tests run fine, it should be noted that 'washington' will be appended
     * during the process. look at LocationAPI.java
     */
    public void testMultipleDrivers() {

        final String input = TestUtility.createTestParticipant("Aaron", "Brooklyn, New York", "rider", 0)
                + "\n" + TestUtility.createTestParticipant("Ted", "New York University", "driver", 4)
                + "\n" + TestUtility.createTestParticipant("Connor", "Boston University", "driver", 5)
                + "\n" + TestUtility.createTestParticipant("Nick", "Northeastern University", "rider", 0)
                + "\n" + TestUtility.createTestParticipant("Tod", "Stony Brook University", "rider",0);

        final RawParticipants result = parser.parseInitialRequestThroughTSV(input);
        Assert.assertEquals(2, result.getDrivers().size());
        Assert.assertEquals(0, result.getUnclassifieds().size());
        Assert.assertEquals(3, result.getRiders().size());
        Assert.assertEquals("Nick", result.getRiders().get(1).getName());
        Assert.assertEquals("100 Nicolls Rd, Stony Brook, NY 11794, USA", result.getRiders().get(2).getAddress());
        Assert.assertEquals(4, result.getDrivers().get(0).getSpaces());
    }


    public void testSheetsAPI() {
        List<List<Object>> vals = new ArrayList<List<Object>>();
        List row1 = new ArrayList();

        row1.add("aaron");
        row1.add("seattle, washington");
        row1.add("driver");
        row1.add("5");
        vals.add(row1);

        final RawParticipants result = parser.parseInitialRequestThroughSheetsWithList(vals);
        Assert.assertEquals(1, result.getDrivers().size());
        Assert.assertEquals("aaron", result.getDrivers().get(0).getName());
        Assert.assertEquals(5, result.getDrivers().get(0).getSpaces());
        Assert.assertEquals("Seattle, WA, USA", result.getDrivers().get(0).getAddress());
    }
}
