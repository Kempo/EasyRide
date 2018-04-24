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
}
