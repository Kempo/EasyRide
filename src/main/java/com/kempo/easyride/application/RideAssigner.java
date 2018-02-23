package com.kempo.easyride.application;
import com.kempo.easyride.google.MapsAPI;
import com.kempo.easyride.model.AssignedRides;
import com.kempo.easyride.model.Person;
import com.kempo.easyride.util.DriverComparator;
import com.kempo.easyride.util.RiderComparator;
import com.kempo.easyride.model.Driver;
import com.kempo.easyride.model.Rider;

import java.util.*;


public class RideAssigner {


    /**
     * responsible for loading all preferences for each driver and now rider
     * @param driverList
     * @param riderList
     */
    public void loadPreferences(List<Driver> driverList, List<Rider> riderList) {

        // loading driver preferences
        for(Driver driver : driverList) {
            for(Rider rider : riderList) {
                double distance = MapsAPI.getDistance(driver.getAddress(), rider.getAddress());
                if(distance < 0) {
                    System.err.println("invalid distance! " + rider.getAddress() + " " + driver.getAddress());
                }else {
                    rider.setDistanceTo(distance);
                    driver.getDriverPreferences().add(rider);
                }
            }
            Collections.sort(driver.getDriverPreferences(), new RiderComparator());
        }

        // loading rider preferences
        for(Rider rider : riderList) {
            for(Driver driver : driverList) {
                double distance = MapsAPI.getDistance(rider.getAddress(),driver.getAddress());

                if(distance < 0) {
                    System.err.println("invalid distance! " + rider.getAddress() + " " + driver.getAddress());
                }else {
                    driver.setDistanceTo(distance);
                    rider.getRiderPreferences().add(driver);
                }
            }
            Collections.sort(rider.getRiderPreferences(), new DriverComparator());
        }


        System.out.println("PREFERENCES:");
        logPreferences(riderList, driverList);
        System.out.println("PREFERENCE END");

    }

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


    /**
     *
     * @param riderList
     * @param driverList
     * @param assignedRides
     */
    public void assignOccupantsHelper(List<Rider> riderList, List<Driver> driverList, final AssignedRides assignedRides) {
        for (Rider rider : riderList) {
            if (rider.getCurrentCar() == null) {
                System.out.println("rider " + rider.getName());
                int currentPref = 0;
                for (Driver driver : rider.getRiderPreferences()) {
                    Driver realDriver = driverList.get(driverList.indexOf(driver));
                    if(realDriver.getCar() != null && !realDriver.getCar().isFull() && highestPreference(riderList, driverList, rider, currentPref, realDriver)) {
                        realDriver.getCar().addOccupant(rider);
                        rider.setCar(realDriver.getCar());
                        break;
                    }
                    System.out.println("moving on from" + driver.getName());
                    currentPref += 1; // as it rotates through each driver
                }
            }
        }

        for (Rider r : riderList) {
            if (r.getCurrentCar() == null) {
                assignedRides.addUnassignedRider(r);
            }
        }
    }

    /**
     *
     * @param riderList
     * @param driverList
     * @param currentRider
     * @param currentPref
     * @param currentDriver
     * @return true if this rider prefers a certain driver the most meaning they should be assigned together.
     */
    public boolean highestPreference(List<Rider> riderList, List<Driver> driverList, Rider currentRider, int currentPref, Driver currentDriver) {
        for (Rider r : riderList) {
            if (!isIdentical(r, currentRider)) {
                int otherPref = 0;
                for (Driver d : r.getRiderPreferences()) {
                    if (isIdentical(d, currentDriver)) {// if this is the right driver to be analyzed
                        if ((otherPref < currentPref) && !currentDriver.getCar().isFull() && r.getCurrentCar() == null && (currentDriver.getCar().getOpenSpots() < (2))) {
                            // if there is a rider out there with a higher preference, and if the current driver's car isn't full, and if the rider doesn't have a car, and if the current  driver can't take the two of them
                            System.out.println("rider " + r.getName() + " has higher pick than " + currentRider.getName() + " to " + currentDriver.getName() + " | " + currentPref + " to " + otherPref);
                            return false; // return false since the other rider should get the seat
                        }
                        if ((otherPref == currentPref) && (currentDriver.getCar().getOpenSpots() < 2) && !r.getAddress().equals(currentRider.getAddress()) && (r.getCurrentCar() == null)) { // if the preference are equal
                            double curDist = MapsAPI.getDistance(currentRider.getAddress(), d.getAddress());
                            double otherDist = MapsAPI.getDistance(r.getAddress(), d.getAddress());
                            return (curDist < otherDist);
                        }
                    }
                    otherPref += 1; // goes to the next driver on the list if the current one isn't viable
                }
            }
        }

        int curDriverPref = currentDriver.getDriverPreferences().indexOf(currentRider);
        for(Driver d : driverList) {
            if(!isIdentical(d, currentDriver) && !d.getCar().isFull()) {
               int otherPref = d.getDriverPreferences().indexOf(currentRider); // simplified version of above. remembered this from previous commits
               if((otherPref < curDriverPref) && (currentRider.getCurrentCar() == null)) {
                   System.out.println("driver " + d.getName() + " has higher pick than " + currentDriver.getName() + " to " + currentRider.getName() + " | " + curDriverPref + " to " + otherPref);
                   return false;
               }

               if(otherPref == curDriverPref) {
                    double curDist = MapsAPI.getDistance(currentDriver.getAddress(), currentRider.getAddress());
                    double otherDist = MapsAPI.getDistance(d.getAddress(), currentRider.getAddress());
                    return (curDist < otherDist);
               }
            }
        }
        return true;
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
