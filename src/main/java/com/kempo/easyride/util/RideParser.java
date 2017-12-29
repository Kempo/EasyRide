package com.kempo.easyride.util;

import com.kempo.easyride.model.RawDriver;
import com.kempo.easyride.model.RawParticipants;
import com.kempo.easyride.model.Rider;
import com.kempo.easyride.model.Unclassified;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * classifies input into riders/drivers/unparseable.
 * added isLocationValid check -aaron
 */
public class RideParser
{

    private static final String DRIVER = "driver";
    private static final String RIDER = "rider";

    public RawParticipants parseInitialRequest(final String rawCsv)
    {
        final String[] lines = rawCsv.split("\n");
        final RawParticipants participants = new RawParticipants();
        for (String line : lines)
        {
            String[] attrs = line.split("\t");
            if (attrs.length < 3 || !isLocationValid(attrs[1]))
            {
                participants.addUnclassified(new Unclassified(line, "length: " + attrs.length + " location: '" + attrs[1] + "'"));
            }
            else if (DRIVER.equals(attrs[2].toLowerCase()))
            {
                parseDriver(participants, line, attrs);
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

    private boolean isLocationValid(String address) {
        String formatted = address.replaceAll(" ", "%20");
        String key = "AIzaSyDeYYFr4IqU9nAvjIzM5NRvWduEkSUEaro";
        String link = "https://maps.googleapis.com/maps/api/geocode/json?address=" + formatted + "&key=" + key;
        try {
            URL url = new URL(link);
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;
            while((line = reader.readLine()) != null) {
                if(line.contains("\"status\" : \"ZERO_RESULTS\"")) {
                    return false;
                }
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    private void parseDriver(final RawParticipants participants, final String line, final String[] attrs)
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
