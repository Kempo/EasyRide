package com.kempo.easyride.model;

import java.util.ArrayList;
import java.util.List;

public class Rider {
    private String name; // name of the rider
    private String address; // his/her address
    private List<Driver> preferences = new ArrayList<>(); // the list of drivers that they prefer (ordered by distance?)
    private Car current; // their current car

    public Rider(String n, String a) {
        name = n;
        address = a;
    }

    public List<Driver> getPreferences() {
        return preferences;
    }

    public Car getCurrentCar() {
        return current;
    }

    public void setCar(Car c) {
        current = c;
    }
}
