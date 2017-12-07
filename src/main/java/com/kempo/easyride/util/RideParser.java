package com.kempo.easyride.util;

import com.kempo.easyride.model.RawDriver;
import com.kempo.easyride.model.RawParticipants;
import com.kempo.easyride.model.Rider;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * all this does is classify input into riders/drivers/unparseable. It makes no judgment on whether or not the input
 * is valid other than that.
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
                participants.addUnclassified(line);
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
                participants.addUnclassified(line);
            }
        }

        return participants;
    }

    private boolean isLocationValid(String address) {
        String formatted = address.replaceAll(" ", "_");
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
            participants.addUnclassified(line);
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
                participants.addUnclassified(line);
            }
        }
    }
}
