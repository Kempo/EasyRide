package com.kempo.easyride.model;

import java.util.ArrayList;
import java.util.List;

public class AssignedRides {
    private final List<Driver> drivers;
    private final List<Rider> unassignedRiders;
    private String unparseable;
    public AssignedRides(final List<Driver> drivers)
    {
        this.drivers = drivers;
        this.unassignedRiders = new ArrayList<>();
    }

    public void addUnassignedRider(final Rider rider)
    {
        this.unassignedRiders.add(rider);
    }

    public List<Driver> getDrivers() {
        return drivers;
    }

    public List<Rider> getUnassignedRiders() { return unassignedRiders; }

    public String getUnparseable() {
        return unparseable;
    }

    public void setUnparseable(String u) {
        unparseable = u;
    }

    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder();
        for (final Driver d : drivers)
        {
            sb.append(d.toString());
        }
        if(!getUnassignedOrUnparseable().isEmpty()) {
            sb.append(getUnassignedOrUnparseable());
        }
        return sb.toString();
    }

    public String getUnassignedOrUnparseable() {
        final StringBuilder sb  = new StringBuilder();
        if(getUnassignedRiders().size() > 0) {
            sb.append("<b>Unassigned riders:</b> <br>");
            for (final Rider r : unassignedRiders) {
                sb.append(r.toString() + "<br>");
            }
        }
        if(!getUnparseable().isEmpty()) {
            sb.append(getUnparseable());
        }
        return sb.toString();
    }

}
