package com.kempo.easyride.util;

import com.kempo.easyride.google.SheetsAPI;
import junit.framework.Assert;
import junit.framework.TestCase;

public class MiscTest extends TestCase {
    public void testURLParser() {
        String URL = "https://docs.google.com/spreadsheets/d/sheetsID/edit#gid=0";
        String ID = SheetsAPI.getIDFromURL(URL);
        Assert.assertEquals("sheetsID", ID);
    }
}
