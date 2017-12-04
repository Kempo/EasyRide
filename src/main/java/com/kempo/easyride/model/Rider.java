package com.kempo.easyride.model;

public class Rider extends Person {

    private Car current = null; // their current car
    private double distance; // distance to a target


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

    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append(name);
        sb.append(" (");
        sb.append(address);
        sb.append(")");
        sb.append("\n");
        return sb.toString();
    }
}
