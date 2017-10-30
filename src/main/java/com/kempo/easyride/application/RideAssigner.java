package com.kempo.easyride.application;
import com.kempo.easyride.model.AssignedRides;
import com.kempo.easyride.util.DistanceComparator;
import com.kempo.easyride.model.Driver;
import com.kempo.easyride.model.Rider;

import java.util.*;


public class RideAssigner {

    private MapsAPI maps = new MapsAPI();

    /**
     * responsible for loading all preferences for each driver
     * @param driverList
     * @param riderList
     */
    public void readData(List<Driver> driverList, List<Rider> riderList) {
        for(Driver driver : driverList) {
            for(Rider rider : riderList) {
                double distance = maps.getDistance(driver.getAddress(), rider.getAddress());
                rider.setDistanceTo(distance);
                driver.getPreferences().add(rider); // unorganized list of riders
            }
            Collections.sort(driver.getPreferences(), new DistanceComparator()); // organizes the list using the DistanceComparator
        }
    }


    /**
     * responsible for assigning occupants to each vehicle
     * @param drivers
     * @param riders
     */
    public AssignedRides assignOccupants(final List<Driver> drivers, final List<Rider> riders)
    {
        final AssignedRides result = new AssignedRides(drivers);
        assignOccupantsHelper(drivers, riders, result);
        return result;
    }


    public void assignOccupantsHelper(List<Driver> driverList, List<Rider> riderList,
       final AssignedRides assignedRides) {
        for (Driver currentDriver : driverList) {
            for (Rider r : currentDriver.getPreferences()) {
                if ((r.getCurrentCar() == null) && !currentDriver.getCar().isFull() && !betterOption(currentDriver, r, driverList)) {
                    currentDriver.getCar().addOccupant(r);
                    r.setCar(currentDriver.getCar());
                }
            }
        }

        if (ridersLeft(riderList)) { // if there are riders without a car
            if(driversOpen(driverList)) {
                assignOccupants(driverList, riderList); // reiterates the whole process if a rider doesn't have a car and not all drivers are filled up
            }
        }

        for (Rider r : riderList) {
            if (r.getCurrentCar() == null) {
                assignedRides.addUnassignedRider(r);
                System.out.println(r.getName() + " has no ride."); // prints out the remaining riders without a ride
            }
        }
    }

    /**
     *
     * @param riderList
     * @return true if there are riders without a car and false if all riders have a driver
     */
    private boolean ridersLeft(List<Rider> riderList) {
        for(Rider rider : riderList) {
            if(rider.getCurrentCar() == null) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param driverList
     * @return true if there are still open cars and false if all cars are full
     */
    private boolean driversOpen(List<Driver> driverList) {
        for(Driver driver : driverList) {
            if(!driver.getCar().isFull()) {
                return true;
            }
        }
        return false;
    }

    /**
     * returns whether or not there is a better and open option for the rider to go to than this current driver
     * @param driver
     * @param rider
     * @param driverList
     * @return
     */
    private boolean betterOption(Driver driver, Rider rider, List<Driver> driverList) {
        int currentPref = driver.getPreferences().indexOf(rider); // current index(rank) in relation to our specified driver
        for(Driver otherDriver : driverList) { // loops through all the drivers
            int otherPref = otherDriver.getPreferences().indexOf(rider);
            if(otherPref < currentPref && !otherDriver.getCar().isFull()) { // if another driver has ranked this rider higher(lower in a literal sense) than our driver's current rank
                return true; // return true that there is a better option so don't add the rider to this specified driver
            }
        }
        return false; // or return false if all other drivers have this rider ranked lower. This driver is the best option for them
    }
}
