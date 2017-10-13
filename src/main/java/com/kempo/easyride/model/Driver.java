package com.kempo.easyride.model;

public class Driver {
    private String address; // driver's address
    private String name; // his/her name
    private Car car; // the car that he has, which contains the # of spots left and current occupants

    public Driver(String n, String a, Car c) {
        address = a;
        name = n;
        car = c;
    }

    public String getAddress() { return address; }

    public String getName() { return name; }

    public Car getCar() { return car; }
}
