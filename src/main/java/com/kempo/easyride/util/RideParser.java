package com.kempo.easyride.util;

import com.google.api.services.sheets.v4.model.ValueRange;
import com.kempo.easyride.google.LocationAPI;
import com.kempo.easyride.model.RawDriver;
import com.kempo.easyride.model.RawParticipants;
import com.kempo.easyride.model.Rider;
import com.kempo.easyride.model.Unclassified;

import java.util.List;

/**
 * classifies input into riders/drivers/unparseable.
 * added isLocationValid check -aaron
 */
public class RideParser
{

    private static final String DRIVER = "driver";
    private static final String RIDER = "rider";

    public RawParticipants parseInitialRequestThroughTSV(final String rawTsv)
    {
        final String[] lines = rawTsv.split("\n");
        final RawParticipants participants = new RawParticipants();
        for (String line : lines)
        {
            String[] attrs = line.split("\t");
            if (attrs.length < 3)
            {
                participants.addUnclassified(new Unclassified(line, "not appropriate format. length: " + attrs.length));
            }else if(!LocationAPI.isLocationValid(attrs[1]))
            {
                participants.addUnclassified(new Unclassified(line, "invalid location: '" + attrs[1] + "'"));
            }
            else if (DRIVER.equals(attrs[2].toLowerCase()))
            {
                parseDriverThroughTSV(participants, line, attrs);
            }
            else if (RIDER.equals(attrs[2].toLowerCase()))
            {
                participants.addRider(new Rider(attrs[0], attrs[1]));
            }
            else
            {
                participants.addUnclassified(new Unclassified(line, "no type designation: '" + attrs[2] + "'"));
            }
        }

        return participants;
    }

    public RawParticipants parseInitialRequestThroughSheets(final ValueRange result) {
        final RawParticipants participants = new RawParticipants();
        final List<List<Object>> values = result.getValues();

        if(values != null && values.size() > 0) {
            for (List row : values) {
                if(row.size() <= 4) {
                    if(!LocationAPI.isLocationValid(row.get(1).toString())) {
                        participants.addUnclassified(new Unclassified(row.toString(), "invalid location: '" + row.get(1).toString() + "'"));
                    }
                    else if(DRIVER.equalsIgnoreCase(row.get(2).toString()))
                    {
                        try {
                            final int spots = Integer.parseInt(row.get(3).toString());
                            participants.addDriver(new RawDriver(row.get(0).toString(), row.get(1).toString(), spots)); // add catch for errors
                        }catch(NumberFormatException e) {
                            participants.addUnclassified(new Unclassified(row.toString(), "incorrect spot designation"));
                            e.printStackTrace();
                        }
                    }
                    else if(RIDER.equalsIgnoreCase(row.get(2).toString()))
                    {
                        participants.addRider(new Rider(row.get(0).toString(), row.get(1).toString()));
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

    public RawParticipants parseInitialRequestThroughSheetsWithList(final List<List<Object>> values) {
        final RawParticipants participants = new RawParticipants();
        if(values != null && values.size() > 0) {
            for (List row : values) {
                if(row.size() <= 4) {
                    if(!LocationAPI.isLocationValid(row.get(1).toString())) {
                        participants.addUnclassified(new Unclassified(row.toString(), "invalid location: '" + row.get(1).toString() + "'"));
                    }
                    else if(DRIVER.equalsIgnoreCase(row.get(2).toString()))
                    {
                        try {
                            final int spots = Integer.parseInt(row.get(3).toString());
                            participants.addDriver(new RawDriver(row.get(0).toString(), row.get(1).toString(), spots)); // add catch for errors
                        }catch(NumberFormatException e) {
                            participants.addUnclassified(new Unclassified(row.toString(), "incorrect spot designation"));
                            e.printStackTrace();
                        }
                    }
                    else if(RIDER.equalsIgnoreCase(row.get(2).toString()))
                    {
                        participants.addRider(new Rider(row.get(0).toString(), row.get(1).toString()));
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

    private void parseDriverThroughTSV(final RawParticipants participants, final String line, final String[] attrs)
    {
        if (attrs.length < 4)
        {
            participants.addUnclassified(new Unclassified(line, "length: " + attrs.length));
        }
        else
        {
            try
            {
                final int spacesInCar = Integer.valueOf(attrs[3]);
                participants.addDriver(new RawDriver(attrs[0], attrs[1], spacesInCar));
            }
            catch (final NumberFormatException e)
            {
                participants.addUnclassified(new Unclassified(line, "incorrect spot designation: '" + attrs[3] + "'"));
            }
        }
    }
}
