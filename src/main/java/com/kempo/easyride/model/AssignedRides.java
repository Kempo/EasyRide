package com.kempo.easyride.model;

import java.util.ArrayList;
import java.util.List;

public class AssignedRides {
    final List<Driver> drivers;
    final List<Rider> unassignedRiders;

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

    public List<Rider> getUnassignedRiders() {
        return unassignedRiders;
    }

    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder();
        for (final Driver d : drivers)
        {
            sb.append(d.toString());
        }
        sb.append("\n");
        sb.append("\n");
        if(getUnassignedRiders().size() > 0) {
            sb.append("Unassigned riders: \n");
            for (final Rider r : unassignedRiders) {
                sb.append(r.toString() + "\n");
            }
        }
        return sb.toString();
    }
}
