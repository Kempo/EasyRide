package com.kempo.easyride.model;

import java.util.ArrayList;
import java.util.List;

public class Driver extends Person {
    private Car car; // the car that he has, which contains the # of spots left and current occupants
    private List<Rider> preferences = new ArrayList<>(); // the list of riders that they prefer (ordered by distance)
    private double distance;

    public Driver(String n, String a, Car c) {
        this.address = a;
        this.name = n;
        car = c;
    }

    public List<Rider> getDriverPreferences() {
        return preferences;
    }

    public Car getCar() { return car; }

    public double getDistanceTo() {
        return distance;
    }

    public void setDistanceTo(double d) {
        distance = d;
    }

    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append("<b>DRIVER:</b> " + name + " (" + address + ") " + "<br>");
        sb.append("RIDERS: <br>");
        List<Rider> occupants = car.getOccupants();
        for (final Rider r : occupants)
        {
            sb.append(r.toString() + "<br>");
        }
        sb.append("<br>");
        return sb.toString();
    }
}
