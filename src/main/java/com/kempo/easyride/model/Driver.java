package com.kempo.easyride.model;

import java.util.ArrayList;
import java.util.List;

public class Driver extends Person {
    private Car car; // the car that he has, which contains the # of spots left and current occupants
    private List<Rider> preferences = new ArrayList<>(); // the list of riders that they prefer (ordered by distance)

    public Driver(String n, String a, Car c) {
        this.address = a;
        this.name = n;
        car = c;
    }

    public List<Rider> getPreferences() {
        return preferences;
    }

    public Car getCar() { return car; }

}
