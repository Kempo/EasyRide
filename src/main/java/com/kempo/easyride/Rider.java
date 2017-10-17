package com.kempo.easyride;

public class Rider {
    private String name; // name of the rider
    private String address; // his/her address
    private Car current; // their current car


    public Rider(String n, String a) {
        name = n;
        address = a;
    }

    public String getName() {
        return name;
    }

    public Car getCurrentCar() {
        return current;
    }

    public void setCar(Car c) {
        current = c;
    }

    public String getAddress() { return address; }
}
