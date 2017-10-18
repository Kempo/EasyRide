package com.kempo.easyride;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * created with assumptions that:
 * 1) riders will be picked up at their house by the driver
 * 2) the distance from the driver's house to the rider's house is the same if it's the rider to the driver
 *     - google maps may actually input differences in distance from rider to driver and driver to rider, but they shouldn't vary too much.
 *
 */

public class EasyRide {
    private static Manager m;

    public static void main(String[] args) {
        m = new Manager();

        /**
         * for testing purposes
         */
        List<Rider> riders = new ArrayList<>();
        List<Driver> drivers = new ArrayList<>();
        riders.add(new Rider("Tim","University of Washington"));
        riders.add(new Rider("Graham", "San Francisco"));
        riders.add(new Rider("Aaron", "Space Needle"));
        riders.add(new Rider("David", "Space Needle"));
        riders.add(new Rider("William","San Diego"));
        riders.add(new Rider("Jack","Los Angeles"));
        drivers.add(new Driver("Rick", "Seattle University", new Car(3)));
        drivers.add(new Driver("Mark", "Los Angeles", new Car(1)));

        m.readData(drivers, riders);
        m.assignOccupants(drivers, riders);

        for(Driver d : drivers) {
            System.out.println(d.getName().toUpperCase());
            for(Rider r : d.getCar().getOccupants()) {
                System.out.println(r.getName());
            }
        }
    }
}
