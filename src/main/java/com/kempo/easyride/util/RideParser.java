package com.kempo.easyride.util;

import com.google.api.services.sheets.v4.model.ValueRange;
import com.kempo.easyride.google.LocationAPI;
import com.kempo.easyride.model.RawDriver;
import com.kempo.easyride.model.RawParticipants;
import com.kempo.easyride.model.Rider;
import com.kempo.easyride.model.Unclassified;

import javax.xml.stream.Location;
import java.util.Arrays;
import java.util.List;

/**
 * classifies input into riders/drivers/unparseable.
 */
public class RideParser
{

    private static final String DRIVER = "driver";
    private static final String RIDER = "rider";

    public RawParticipants parseInitialRequestThroughCrawler(Crawler crawler) {
        final RawParticipants participants = new RawParticipants();
        final List<List<Object>> values = crawler.getValueRange().getValues();
        if(values != null && values.size() > 0) {
            for(int rowIndex = 1; rowIndex < values.size(); rowIndex++) {
                List<Object> rowValues = values.get(rowIndex);

                String colValue = rowValues.get(crawler.getAddressColumn()).toString();
                String address = LocationAPI.fetchFormatted(colValue);

                if(address == null) {
                    participants.addUnclassified(new Unclassified(colValue, "invalid location"));
                }else{

                    final String name = rowValues.get(crawler.getNameColumn()).toString();
                    final String desig = rowValues.get(crawler.getDesignationColumn()).toString();

                    if(crawler.isStringInList(desig, Keywords.DRIVER)) {
                        final int spots = Integer.parseInt(rowValues.get(crawler.getSpotsColumn()).toString()); // make this parsing process better (account for words for numbers)
                        participants.addDriver(new RawDriver(name, address, spots));
                    }else if(crawler.isStringInList(desig, Keywords.RIDER)) {
                        participants.addRider(new Rider(name, address));
                    }else {
                        participants.addUnclassified(new Unclassified(colValue, "no designations found."));
                    }
                }
            }
        }

        return participants;
    }


    public RawParticipants parseInitialRequestThroughTSV(final String rawTsv)
    {
        final String[] lines = rawTsv.split("\n");
        final RawParticipants participants = new RawParticipants();
        for (String line : lines)
        {
            String[] attrs = line.split("\t");
            if(attrs.length < 3) {
                participants.addUnclassified(new Unclassified(line, "not appropriate format. length: " + attrs.length));
            }else {
                String address = LocationAPI.fetchFormatted(attrs[1]); // the reformatted address that will specify more details if not given
                if (address == null) // if the location is not valid
                {
                    participants.addUnclassified(new Unclassified(line, "invalid location: '" + attrs[1] + "'"));
                } else if (DRIVER.equals(attrs[2].toLowerCase())) {
                    parseDriverThroughTSV(participants, line, attrs, address);
                } else if (RIDER.equals(attrs[2].toLowerCase())) {
                    participants.addRider(new Rider(attrs[0], address));  // adds the rider with the new formatted address
                } else {
                    participants.addUnclassified(new Unclassified(line, "no type designation: '" + attrs[2] + "'"));
                }
            }
        }

        return participants;
    }

    public RawParticipants parseInitialRequestThroughSheets(final ValueRange result, final List<List<Object>> v) {
        final RawParticipants participants = new RawParticipants();
        final List<List<Object>> values = (v == null ? result.getValues() : v);

        if(values != null && values.size() > 0) {
            for (List row : values) {
                if(row.size() <= 4) {
                    String address = LocationAPI.fetchFormatted(row.get(1).toString());
                    if(address == null) {
                        participants.addUnclassified(new Unclassified(row.toString(), "invalid location: '" + row.get(1).toString() + "'"));
                    }
                    else if(DRIVER.equalsIgnoreCase(row.get(2).toString()))
                    {
                        try {
                            final int spots = Integer.parseInt(row.get(3).toString());
                            participants.addDriver(new RawDriver(row.get(0).toString(), address, spots)); // add catch for errors
                        }catch(NumberFormatException e) {
                            participants.addUnclassified(new Unclassified(row.toString(), "incorrect spot designation"));
                            e.printStackTrace();
                        }
                    }
                    else if(RIDER.equalsIgnoreCase(row.get(2).toString()))
                    {
                        participants.addRider(new Rider(row.get(0).toString(), address));
                    }
                    else {
                        participants.addUnclassified(new Unclassified(row.toString(), "no type designation: '" + row.get(2).toString() + "'"));
                    }
                }else{
                    participants.addUnclassified(new Unclassified(row.toString(), "invalid row length: " + row.size()));
                }
            }
        }
        return participants;
    }

    /*
    public RawParticipants parseInitialRequestThroughSheetsWithList(final List<List<Object>> values) {
        final RawParticipants participants = new RawParticipants();
        if(values != null && values.size() > 0) {
            for (List row : values) {
                if(row.size() <= 4) {
                    String address = LocationAPI.fetchFormatted(row.get(1).toString());
                    if(address == null) {
                        participants.addUnclassified(new Unclassified(row.toString(), "invalid location: '" + row.get(1).toString() + "'"));
                    }
                    else if(DRIVER.equalsIgnoreCase(row.get(2).toString()))
                    {
                        try {
                            final int spots = Integer.parseInt(row.get(3).toString());
                            participants.addDriver(new RawDriver(row.get(0).toString(), address, spots)); // add catch for errors
                        }catch(NumberFormatException e) {
                            participants.addUnclassified(new Unclassified(row.toString(), "incorrect spot designation"));
                            e.printStackTrace();
                        }
                    }
                    else if(RIDER.equalsIgnoreCase(row.get(2).toString()))
                    {
                        participants.addRider(new Rider(row.get(0).toString(), address));
                    }
                    else {
                        participants.addUnclassified(new Unclassified(row.toString(), "no type designation: '" + row.get(2).toString() + "'"));
                    }
                }else{
                    participants.addUnclassified(new Unclassified(row.toString(), "invalid row length: " + row.size()));
                }
            }
        }
        return participants;
    }
     */

    private void parseDriverThroughTSV(final RawParticipants participants, final String line, final String[] attrs, final String addr)
    {
        if (attrs.length < 4)
        {
            participants.addUnclassified(new Unclassified(line, "length: " + attrs.length));
        }
        else
        {
            try
            {
                final int spacesInCar = Integer.parseInt(attrs[3]);
                participants.addDriver(new RawDriver(attrs[0], addr, spacesInCar));
            }
            catch (final NumberFormatException e)
            {
                participants.addUnclassified(new Unclassified(line, "incorrect spot designation: '" + attrs[3] + "'"));
            }
        }
    }
}
