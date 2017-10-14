package com.kempo.easyride.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dileng on 10/12/17.
 */
public class RawParticipants
{
    private List<RawDriver> drivers;
    private List<RawRider> riders;
    private List<String> unclassifieds;

    public RawParticipants()
    {
        drivers = new ArrayList<>();
        riders = new ArrayList<>();
        unclassifieds = new ArrayList<>();
    }

    public void addRider(final RawRider rider)
    {
        riders.add(rider);
    }

    public void addDriver(final RawDriver driver)
    {
        drivers.add(driver);
    }

    public void addUnclassified(final String unclassified)
    {
        unclassifieds.add(unclassified);
    }

    public List<RawDriver> getDrivers()
    {
        return drivers;
    }

    public List<RawRider> getRiders()
    {
        return riders;
    }

    public List<String> getUnclassifieds()
    {
        return unclassifieds;
    }

    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append("Riders: \n");
        for (final RawRider rider : riders)
        {
            sb.append(rider + "\n");
        }
        sb.append("Drivers: \n");
        for (final RawDriver driver : drivers)
        {
            sb.append(driver + "\n");
        }
        if (unclassifieds.size() > 0)
        {
            sb.append("We weren't able to process these lines:\n");
            for (final String line : unclassifieds)
            {
                sb.append(line);
                sb.append("\n");
            }
        }
        return sb.toString();
    }
}
