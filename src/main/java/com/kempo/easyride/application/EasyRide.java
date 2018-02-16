package com.kempo.easyride.application;


import com.kempo.easyride.model.Driver;
import com.kempo.easyride.model.Rider;

import java.util.List;

public class EasyRide {

    public static void main(String[] args) {
        System.out.println("Hello!");
    }

    public static Rider getRiderByName(String name, List<Rider> riders) {
        for(Rider r : riders) {
            if(r.getName().equalsIgnoreCase(name)) {
                return r;
            }
        }
        return null;
    }

    public static Driver getDriverByName(String name, List<Driver> drivers) {
        for(Driver d : drivers) {
            if(d.getName().equalsIgnoreCase(name)) {
                return d;
            }
        }
        return null;
    }
}
