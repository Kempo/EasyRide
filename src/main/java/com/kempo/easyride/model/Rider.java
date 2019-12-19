package com.kempo.easyride.model;

import java.util.ArrayList;
import java.util.List;

public class Rider extends Person {

    private Car current; // their current car
    private final List<Driver> preferences = new ArrayList<Driver>();

    public Rider(String n, String a) {
        super(n, a);
        this.current = null;
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

            if(other.isIdentical(d)) {
                return true;
            }

            if(!realDriver.getCar().isFull()) {
                return false;
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
