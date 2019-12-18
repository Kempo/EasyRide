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
                double distance = MapsAPI.getDistance(driver.getAddress(), rider.getAddress());
                rider.setDistanceTo(distance);
                driver.getDriverPreferences().add(rider);
            }
            Collections.sort(driver.getDriverPreferences(), new PersonComparator());
        }

        // loading rider preferences
        for(Rider rider : riderList) {
            for(Driver driver : driverList) {
                double distance = MapsAPI.getDistance(rider.getAddress(),driver.getAddress());
                driver.setDistanceTo(distance);
                rider.getRiderPreferences().add(driver);
            }
            Collections.sort(rider.getRiderPreferences(), new PersonComparator());
        }
        // logPreferences(riderList, driverList);
    }

    /*
    public void logPreferences(List<Rider> riderList, List<Driver> driverList) {
        for(Rider r : riderList) {
            System.out.println(r.getName().toUpperCase());
            for(Driver d : r.getRiderPreferences()) {
                System.out.println(d.getName());
            }
        }
        System.out.println();
        for(Driver d : driverList) {
            System.out.println(d.getName().toUpperCase());
            for(Rider r : d.getDriverPreferences()) {
                System.out.println(r.getName());
            }
        }
    }
    */


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

        /*
        for (Rider rider : riderList) {
            if (rider.getCurrentCar() == null) { // if the rider needs a ride
                System.out.println("rider " + rider.getName());
                for (Driver driver : rider.getRiderPreferences()) {
                    System.out.println("testing " + driver.getName());
                    Driver realDriver = driverList.get(driverList.indexOf(driver)); // gets the actual driver from the official driver list
                    int currentDriverPref = realDriver.getDriverPreferences().indexOf(rider); // the select driver's preference of the rider
                    if(realDriver.getCar() != null && !realDriver.getCar().isFull()) { // if the driver's car is available to be filled
                        if(highestPreference(riderList, driverList, rider, realDriver,currentDriverPref)) {
                            realDriver.getCar().addOccupant(rider); // add the occupants and set car for the rider
                            rider.setCar(realDriver.getCar());
                            break; // breaks off from addressing the other driver's in the preference list since the rider has been assigned and continues to the next rider
                        }
                    }
                }
            }
        }

        for (Rider r : riderList) {
            if (r.getCurrentCar() == null) {
                assignedRides.addUnassignedRider(r);
            }
        }
        */
    }

    /*
    private boolean highestPreference(List<Rider> riderList, List<Driver> driverList, Rider currentRider, Driver currentDriver, int curDriverPref) {
        int end = curDriverPref;
        int stopIndex = currentDriver.getCar().getOpenSpots() - 1;
        int inc = 0;
        System.out.println(currentDriver.getName() + " prefers " + currentRider.getName() + " at " + end);

        if(!strongestDriverPreference(currentDriver, currentRider, driverList)) {
            if (end != 0) {
                for (int i = 0; i < (end); i++) {
                    Rider r = currentDriver.getDriverPreferences().get(i); // gets all the riders before our currentRider on the driver's preference list
                    if(r.getCurrentCar() != null || !strongestRiderPreference(r, currentDriver, riderList)) {
                            stopIndex += 1;
                            inc += 1;
                            System.out.println("adding to stop index due to " + r.getName() + " in driver's list.");
                    }
                    else {
                        if (r.getCurrentCar() == null
                                && strongestRiderPreference(r, currentDriver, riderList)
                                && end > stopIndex
                                &&  currentDriver.getCar().getOpenSpots() < 2) {
                            System.out.println("returning false due to other rider " + r.getName());
                            return false;
                        }
                    }
                }

                System.out.println("stop index=" + stopIndex);
                if(end <= stopIndex) {
                    System.out.println("returning true " + " driver " + currentDriver.getName() + " and " + currentRider.getName() + " are a match.");
                    return true;
                }else if (end > stopIndex) {
                    System.out.println("returning false. no match with " + currentDriver.getName() + " and " + currentRider.getName());
                    return false;
                }
            }
        }

        return true;
    }
    */

    /**
     *
     * @param currentRider
     * @param currentDriver
     * @param riderList
     * @return true if this rider has the strongest preference for "currentDriver" or false if another rider has a stronger preference for the driver

    private boolean strongestRiderPreference(Rider currentRider, Driver currentDriver, List<Rider> riderList) {
        int currentPref = currentRider.getRiderPreferences().indexOf(currentDriver);
        for (Rider r : riderList) {
            if (!isIdentical(r, currentRider) && r.getCurrentCar() == null) {
                int otherPref = 0;
                for (Driver d : r.getRiderPreferences()) {
                    if (isIdentical(d, currentDriver)) {// if this is the right driver to be analyzed
                        if ((otherPref < currentPref) && !currentDriver.getCar().isFull()) {
                            return false; // return false since the other rider should get the seat
                        }

                        if ((otherPref == currentPref) && (r.getCurrentCar() == null)) { // if the preference are equal
                            if(r.getAddress().equals(currentRider.getAddress())) {
                                return true;
                            }

                            double curDist = MapsAPI.getDistance(currentRider.getAddress(), d.getAddress());
                            double otherDist = MapsAPI.getDistance(r.getAddress(), d.getAddress());
                            return (curDist < otherDist);
                        }
                    }
                    otherPref += 1; // goes to the next driver on the list if the current one isn't viable
                }
            }
        }
        return true;
    }
    */

    /**
     *
     * @param currentDriver
     * @param currentRider
     * @param driverList
     * @return true if this driver prefers "currentRider" the most or false if another driver has a stronger preference for the rider

    private boolean strongestDriverPreference(Driver currentDriver, Rider currentRider, List<Driver> driverList) {
        int currentPref = currentDriver.getDriverPreferences().indexOf(currentRider);
        for(Driver d : driverList) { // loops through all over drivers that isn't our current one and checks whether they prefer this rider more than our current one
            if(!isIdentical(d, currentDriver)) {
                int otherPref = 0;
                for(Rider r : d.getDriverPreferences()) {
                    if(r.getCurrentCar() == null && isIdentical(r, currentRider)) {
                        if ((otherPref < currentPref)) {
                            System.out.println("driver "  + d.getName() + " has higher preference for " + currentRider.getName());
                            return false;
                        }

                    }
                    otherPref += 1;
                }
            }
        }
        return true;
    }
    */

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
