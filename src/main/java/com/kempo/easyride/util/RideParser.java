package com.kempo.easyride.util;

import com.kempo.easyride.model.RawDriver;
import com.kempo.easyride.model.RawParticipants;
import com.kempo.easyride.model.RawRider;

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
            if (attrs.length < 3)
            {
                participants.addUnclassified(line);
            }
            if (DRIVER.equals(attrs[2].toLowerCase()))
            {
                final int spaces = Integer.valueOf(attrs[3]);
                participants.addDriver(new RawDriver(attrs[0], attrs[1], spaces));
            }
            else if (RIDER.equals(attrs[2].toLowerCase()))
            {
                participants.addRider(new RawRider(attrs[0], attrs[1]));
            }
            else
            {
                participants.addUnclassified(line);
            }
        }

        return participants;
    }
}
