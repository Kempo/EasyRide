package com.kempo.easyride.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dileng on 10/12/17.
 */
public class RawParticipants
{
    private List<RawDriver> drivers;
    private List<Rider> riders;
    private List<Unclassified> unclassifieds;

    public RawParticipants()
    {
        drivers = new ArrayList<>();
        riders = new ArrayList<>();
        unclassifieds = new ArrayList<>();
    }

    public void addRider(final Rider rider)
    {
        riders.add(rider);
    }

    public void addDriver(final RawDriver driver)
    {
        drivers.add(driver);
    }

    public void addUnclassified(final Unclassified unclassified) { unclassifieds.add(unclassified); }

    public List<RawDriver> getDrivers()
    {
        return drivers;
    }

    public List<Rider> getRiders()
    {
        return riders;
    }

    public List<Unclassified> getUnclassifieds()
    {
        return unclassifieds;
    }

    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append("Riders: \n");
        for (final Rider rider : riders)
        {
            sb.append(rider + "\n");
        }
        sb.append("Drivers: \n");
        for (final RawDriver driver : drivers)
        {
            sb.append(driver + "\n");
        }

        return sb.toString();
    }

    public String getUnclassifiedOutput() {
        final StringBuilder sb = new StringBuilder();
        if (unclassifieds.size() > 0)
        {
            sb.append("<b>Unprocessed lines:</b>\n");
            for (final Unclassified u : unclassifieds)
            {
                sb.append("'" + u.getLine() + "'" + "\n");
                sb.append("reason= " + u.getReason());
                sb.append("\n");
            }
        }
        return sb.toString();
    }
}
