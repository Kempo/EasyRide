package com.kempo.easyride.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
            System.out.println(d.getName());
            sb.append(d.toString());
        }
        if(!getUnassignedOrUnparseable().isEmpty()) {
            sb.append(getUnassignedOrUnparseable());
        }
        return sb.toString();
    }

    public String outputJSON() {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        String r = gson.toJson(drivers);
        return r;
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
            sb.append(getUnparseable()); // a single string split by <br> tags
        }
        return sb.toString();
    }

    public void printAssignedRidesToConsole() {
        System.out.println("ASSIGNMENTS:");
        for(Driver d : getDrivers()) {
            System.out.println(d.getName().toUpperCase());
            for(Rider r : d.getCar().getOccupants()) {
                System.out.println(r.getName());
            }
        }
    }
}
