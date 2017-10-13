package com.kempo.easyride.util;

import com.kempo.easyride.model.RawParticipants;
import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * Created by dileng on 10/12/17.
 */
public class RideParserTest extends TestCase
{
    private RideParser parser = new RideParser();

    public void testEntryUnparsedWhenCannotParseLabelColumn()
    {
        final String input = "Bilbo Baggins\t4 main street\thobbit" +
                "\nGandalf\t4 amin street\tdriver\t2";
        final RawParticipants result = parser.parseInitialRequest(input);
        Assert.assertEquals(1, result.getDrivers().size());
        Assert.assertEquals("Gandalf", result.getDrivers().get(0).getName());
        Assert.assertEquals("4 amin street", result.getDrivers().get(0).getAddress());
        Assert.assertEquals(2, result.getDrivers().get(0).getSpaces());
        Assert.assertEquals(1, result.getUnclassifieds().size());
        Assert.assertEquals("Bilbo Baggins\t4 main street\thobbit", result.getUnclassifieds().get(0));
    }

    public void testEntry()
    {
        Integer i = Integer.valueOf("abasf");
        System.out.println(i);
    }
}
