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

        List<Rider> riders = new ArrayList<Rider>();
        List<Driver> drivers = new ArrayList<Driver>();
        riders.add(new Rider("Tim","University of Washington"));
        riders.add(new Rider("Graham", "San Francisco"));
        riders.add(new Rider("Aaron", "8659 Inverness Drive NE 98115"));

        drivers.add(new Driver("Rick", "Seattle University", new Car(4)));
        drivers.add(new Driver("Mark", "Los Angeles", new Car(3)));

        m.readData(drivers, riders);
        m.assignOccupants(drivers);

        for(Driver d : drivers) {
            System.out.println(d.getName().toUpperCase());
            for(Rider r : d.getCar().getOccupants()) {
                System.out.println(r.getName());
            }
        }

        /** drivers assigned because they are closest to the driver ONLY. no additional distance to destination check implemented
         * expected output is:
         * RICK:
         * Tim
         * Aaron
         * MARK:
         * Graham
         */
    }
}
