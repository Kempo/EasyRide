package com.kempo.easyride;

import java.util.ArrayList;
import java.util.List;

public class Driver {
    private String address; // driver's address
    private String name; // his/her name
    private Car car; // the car that he has, which contains the # of spots left and current occupants
    private List<Rider> preferences = new ArrayList<>(); // the list of riders that they prefer (ordered by distance)


    public Driver(String n, String a, Car c) {
        address = a;
        name = n;
        car = c;
    }

    public List<Rider> getPreferences() {
        return preferences;
    }

    public String getAddress() { return address; }

    public String getName() { return name; }

    public Car getCar() { return car; }

}
