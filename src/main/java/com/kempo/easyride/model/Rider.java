package com.kempo.easyride.model;

import java.util.ArrayList;
import java.util.List;

public class Rider extends Person {

    private Car current = null; // their current car
    private final List<Driver> preferences = new ArrayList<Driver>();

    public Rider(String n, String a) {
        super();
        this.name = n;
        this.address = a;
    }

    public Car getCurrentCar() {
        return current;
    }

    public void setCar(Car c) {
        current = c;
    }

    public List<Driver> getRiderPreferences() {
        return preferences;
    }

    public boolean prefersStrongest(List<Driver> real, Driver d) {
        for(Driver other : preferences) {
            Driver realDriver = real.get(real.indexOf(other));

            if(!realDriver.getCar().isFull() && !other.name.equals(d.name)) {
                return false;
            }
            if(other.name.equals(d.name)) {
                return true;
            }
        }
        return true;
    }

    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append(name + " (" + address + ")");

        return sb.toString();
    }
}
