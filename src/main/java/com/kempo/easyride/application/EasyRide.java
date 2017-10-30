package com.kempo.easyride.application;
import com.kempo.easyride.model.Car;
import com.kempo.easyride.model.Driver;
import com.kempo.easyride.model.Rider;

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
    private static RideAssigner m;
    private static MapsAPI maps;
    public static void main(String[] args) {
        m = new RideAssigner();
        maps = new MapsAPI();
        /**
         * for testing purposes
         */

        List<Rider> riders = new ArrayList<>();
        List<Driver> drivers = new ArrayList<>();
        riders.add(new Rider("Tim","University of Washington"));
        riders.add(new Rider("Graham", "San Francisco"));
        riders.add(new Rider("Aaron", "Space Needle"));
        riders.add(new Rider("Matthew", "Seattle Central College"));
        riders.add(new Rider("Will", "Golden Gate Bridge"));


        drivers.add(new Driver("Rick", "Seattle University", new Car(5)));
        drivers.add(new Driver("Mark", "Los Angeles", new Car(5)));

        m.readData(drivers, riders);

        for(Driver d : drivers) {
            System.out.println(d.getName().toUpperCase());
            int rank = 1;
            for(Rider r : d.getPreferences()) {
                System.out.println("RANK: " + rank + " NAME:" + r.getName() + " distance to " + d.getName() + ": distanceTo()=" + r.getDistanceTo() + " mapsAPI=" + maps.getDistance(d.getAddress(), r.getAddress()));
                rank += 1;
            }
        }
    }
}
