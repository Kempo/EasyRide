package com.kempo.easyride.application;
import com.kempo.easyride.google.MapsAPI;
import com.kempo.easyride.model.AssignedRides;
import com.kempo.easyride.model.Person;
import com.kempo.easyride.util.PersonComparator;
import com.kempo.easyride.model.Driver;
import com.kempo.easyride.model.Rider;

import java.util.*;


public class RideAssigner {


    /**
     * responsible for loading all preferences for each driver and rider
     * @param driverList
     * @param riderList
     */
    public void loadPreferences(List<Driver> driverList, List<Rider> riderList) {

        // loading driver preferences
        for(Driver driver : driverList) {
            for(Rider rider : riderList) {
                double distance = MapsAPI.fetchDistance(driver.getAddress(), rider.getAddress());
                rider.setDistanceTo(distance);
                driver.getDriverPreferences().add(rider);
            }
            Collections.sort(driver.getDriverPreferences(), new PersonComparator());
        }

        // loading rider preferences
        for(Rider rider : riderList) {
            for(Driver driver : driverList) {
                double distance = MapsAPI.fetchDistance(rider.getAddress(),driver.getAddress());
                driver.setDistanceTo(distance);
                rider.getRiderPreferences().add(driver);
            }
            Collections.sort(rider.getRiderPreferences(), new PersonComparator());
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
        assignOccupantsHelper(riders, drivers, result);
        return result;
    }

    public void assignOccupantsHelper(List<Rider> riderList, List<Driver> driverList, final AssignedRides assignedRides) {
        /*
            TODO: warning if there are not enough spots for each rider
         */

        int totalRiders = riderList.size();
        int filled = 0;

        while(filled < totalRiders) {
            for(Driver driver : driverList) {
                if(!driver.getCar().isFull()) {
                    for(Rider r : driver.getDriverPreferences()) {
                        Rider actualRider = riderList.get(riderList.indexOf(r));
                        if(actualRider.getCurrentCar() == null && actualRider.prefersStrongest(driverList, driver)) {
                            System.out.println(actualRider.getName() + " m " + driver.getName());
                            driver.getCar().addOccupant(actualRider);
                            actualRider.setCar(driver.getCar());
                            filled++;

                            if(driver.getCar().isFull()) {
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     *
     * @param p1
     * @param p2
     * @return whether participants are identical (in terms of address and name)
     */
    private boolean isIdentical(Person p1, Person p2) {
        return (p1.getName().equals(p2.getName()) && (p1.getAddress().equals(p2.getAddress())));
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
}
