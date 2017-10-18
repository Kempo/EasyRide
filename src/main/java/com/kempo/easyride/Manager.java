package com.kempo.easyride;
import java.util.*;


public class Manager {

    private MapsAPI maps = new MapsAPI();

    /**
     * responsible for loading all preferences for each driver
     * @param driverList
     * @param riderList
     */
    public void readData(List<Driver> driverList, List<Rider> riderList) {
        for(Driver driver : driverList) {
            ArrayList<Double> distanceList = new ArrayList<>();
            for(Rider rider : riderList) {
                double distance = maps.getDistance(driver.getAddress(), rider.getAddress());
                distanceList.add(distance); // an unorganized list of distances of each rider to the driver
            }
            Collections.sort(distanceList); // organize this list of distances based on which distance is closest
            for(Double dist : distanceList) { // loops through this organized list
                for(Rider rider : riderList) { // loops through each rider again
                    double distance = maps.getDistance(driver.getAddress(), rider.getAddress()); // gets the distance between r and d
                    if(distance == dist) { // if the distance is equal to what's next on the list, add that driver.
                        if(!driver.getPreferences().contains(rider)) {
                            driver.getPreferences().add(rider);
                        }
                    }
                }
            }
        }
    }

    /**
     * responsible for assigning occupants to each vehicle
     * @param driverList
     * @param riderList
     */
    public void assignOccupants(List<Driver> driverList, List<Rider> riderList) {
        for(Driver currentDriver : driverList) {
            for(Rider r : currentDriver.getPreferences()) {
                if((r.getCurrentCar() == null) && !currentDriver.getCar().isFull() && !betterOption(currentDriver, r, driverList)) {
                    currentDriver.getCar().addOccupant(r);
                    r.setCar(currentDriver.getCar());
                }
            }
        }

        for(Rider r : riderList) {
            if(r.getCurrentCar() == null) {
                for(Driver d : driverList) {
                    if(!d.getCar().isFull()) {
                        assignOccupants(driverList, riderList); // reiterates the whole process if a rider doesn't have a car and not all drivers are filled up
                        break;
                    }
                }
            }
        }

        for(Rider r : riderList) { // in my opinion, all these for loops seem very very redundant and inefficient. I'll try to redo some of this stuff soon
            if(r.getCurrentCar() == null) {
                System.out.println(r.getName() + " has no ride.");
            }
        }
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
