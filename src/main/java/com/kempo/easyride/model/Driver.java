package com.kempo.easyride.model;

import java.util.ArrayList;
import java.util.List;

public class Driver extends Person {
    private Car car; // the car that he has, which contains the # of spots left and current occupants
    private List<Rider> preferences = new ArrayList<>(); // the list of riders that they prefer (ordered by distance)

    public Driver(String n, String a, Car c) {
        super(n, a);
        car = c;
    }

    public List<Rider> getDriverPreferences() {
        return preferences;
    }

    public Car getCar() { return car; }

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
