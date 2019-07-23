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
        if (values != null) { // if our current sheets data set is available
            final List<List<Object>> rows = values.getValues();
            mainLoop : for (List currentRow : rows) { // loops through all the rows as a block; this will stop looping once all data columns are identified
                if (currentRow.size() >= minimumRowLength) { // if the row size (# of columns) fits criteria for identifying columns
                    for (int colIndex = 0; colIndex < currentRow.size(); colIndex++) { // loops through all the columns in that row
                        String colValue = currentRow.get(colIndex).toString(); // gets the value of each column
                        if (colValue != null && !colValue.isEmpty()) { // if the value in each column is a question and isn't null or empty
                            for (String[] items : Keywords.requirements) {
                                for (String k : items) {
                                    if (colValue.toLowerCase().contains(k)) { // if the column value contains any of the strings in the two-dimensional array requirements
                                        // set columns for designation if the data columns have not been set already.
                                        if (isStringInList(colValue, Keywords.ADDRESSES) && addressCol == -1) {
                                            System.out.println(colValue);
                                            addressCol = colIndex;
                                        }

                                        if (isStringInList(colValue, Keywords.NAMES) && nameCol == -1) {
                                            nameCol = colIndex;
                                        }

                                        if(colValue.contains("?")) {
                                            if(spotsCol == -1 && designationCol != -1) {
                                                spotsCol = colIndex;
                                            }else if(spotsCol != -1 && designationCol == -1) {
                                                designationCol = colIndex;
                                            }else if(spotsCol == -1 && designationCol == -1) {
                                                boolean decider = getSimilarity(colValue, Keywords.SIZE) > getSimilarity(colValue, Keywords.DQUESTION);
                                                if(decider) {
                                                    spotsCol = colIndex;
                                                }else if (decider == false){
                                                    designationCol = colIndex;
                                                }
                                            }
                                        }

                                        if (nameCol != -1 && addressCol != -1 && designationCol != -1 && spotsCol != -1) { // if all columns needed are identified
                                            System.out.println("All columns identified.");
                                            break mainLoop; // breaks out of the mainloop block
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
    public boolean isStringInList(String inputStr, String[] items) {
        return Arrays.stream(items).parallel().anyMatch(inputStr::contains);
    }

    /**
     * @return ValueRange object that contains all the spreadsheet values
     * @throws IOException
     */
    private final ValueRange generateValues() throws IOException {
        return (sheetsID != null || !sheetsID.isEmpty()) ? ((service.spreadsheets().values().get(sheetsID, "A:Z").setKey(SheetsAPI.API_KEY).execute())) : null;
    }

    /**
     *
     * @param element
     * @param array
     * @return the number of components a string has that is in a string array
     */
    private final double getSimilarity(String element, String[] array) {
        return Arrays.stream(array).parallel().filter(element::contains).count();
    }
}
