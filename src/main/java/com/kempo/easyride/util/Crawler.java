package com.kempo.easyride.util;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.kempo.easyride.google.SheetsAPI;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Crawler {

    private final int minimumRowLength = 4; // name, address, rider/driver designation, # of spots

    private final Sheets service;
    private final ValueRange values;
    private final String sheetsID;

    private int nameCol = -1, addressCol = -1, designationCol = -1, spotsCol = -1; // default = -1
    private int startingRow = -1; // starting row that contains all the column sets
    /**
     * on initialization, Crawler will store the values given and then utilise them for parsing.
     * I could honestly just pass everything through parameters though, but that might be a little messy.
     * @param service
     * @param sheetsID
     * @throws IOException
     */
    public Crawler(Sheets service, String sheetsID) throws IOException {
        this.service = service;
        this.sheetsID = sheetsID;
        this.values = generateValues();
        crawl();
    }

    private final List getList(String[] obj) {
        return Arrays.asList(obj);
    }

    public int getNameColumn() { return nameCol; }
    public int getAddressColumn() { return addressCol; }
    public int getDesignationColumn() { return designationCol; }
    public int getSpotsColumn() { return spotsCol; }
    public ValueRange getValueRange() { return values; }
    public int getStartingRow() { return startingRow; }

    /**
     * Reads through all the data and notes each column that contains the wanted values (ie. address, name, rider/driver)
     * Intended for a Google form output into a user's sheets file.
     */
    private final void crawl() {
        if(values != null) {
            final List<List<Object>> rows = values.getValues();
            for(List currentRow : rows) { // loops through all the rows
                if(currentRow.size() >= minimumRowLength) { // if the row size (# of columns) fits criteria for identifying columns
                    for(int colIndex = 0; colIndex < currentRow.size(); colIndex++) { // loops through all the columns in that row
                        String colValue = currentRow.get(colIndex).toString(); // gets the value of each column
                        if(colValue != null && colValue.length() > 2) { // if the value in each column isn't null or empty
                            mainloop: // main loop block
                            {
                                for (String[] items : Keywords.requirements) {
                                    for (String k : items) {
                                        if (colValue.toLowerCase().contains(k)) {
                                            // set columns for designation
                                            if (isStringInList(colValue, Keywords.ADDRESSES)) {
                                                addressCol = colIndex;
                                            }

                                            if (isStringInList(colValue, Keywords.NAMES)) {
                                                nameCol = colIndex;
                                            }

                                            if (isStringInList(colValue, Keywords.SIZE)) {
                                                spotsCol = colIndex;
                                            }

                                            for (String[] i : Keywords.DESIGNATIONS) {
                                                for (String s : i) {
                                                    if (colValue.contains(s)) {
                                                        designationCol = colIndex;
                                                        break; // breaks out of current loop
                                                    }
                                                }
                                            }

                                            if (nameCol != -1 && addressCol != -1 && designationCol != -1 && spotsCol != -1) { // if all columns needed are identified
                                                System.out.println("All columns identified.");
                                                break mainloop; // breaks out of the mainloop block
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     *
     * @param inputStr
     * @param items
     * @return true if a string has components that is in a list and false if it doesn't. credits to StackOverFlow java-ers
     */
    private boolean isStringInList(String inputStr, String[] items) {
        return Arrays.stream(items).parallel().anyMatch(inputStr::contains);
    }

    /**
     * @return ValueRange object that contains all the spreadsheet values
     * @throws IOException
     */
    private final ValueRange generateValues() throws IOException {
        return service.spreadsheets().values().get(sheetsID, "A:Z").setKey(SheetsAPI.API_KEY).execute();
    }
}
