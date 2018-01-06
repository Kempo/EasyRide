package com.kempo.easyride.model;

public class Rider extends Person {

    private Car current = null; // their current car
    private double distance; // distance to a target
    private boolean convenience;

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

    public void setDistanceTo(double d) {
        distance = d;
    }

    public double getDistanceTo() {
        return distance;
    }

    public boolean isConvenientTo() { return convenience; }

    public void setConvenience(boolean c) { convenience = c; }


    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append(name + " (" + address + ")");

        return sb.toString();
    }
}
