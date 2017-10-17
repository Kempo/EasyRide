package com.kempo.easyride;
import java.util.*;


public class Manager {

    private List<Driver> drivers = new ArrayList<>(); // hold on is this even necessary
    private List<Rider> riders = new ArrayList<>();
    private MapsAPI maps = new MapsAPI();

    /**
     * responsible for loading all preferences for each driver
     * @param d
     * @param r
     */
    public void readData(List<Driver> d, List<Rider> r) {
        for(Driver driver : d) {
            ArrayList<Double> distanceList = new ArrayList<>();
            for(Rider rider : r) {
                double distance = maps.getDistance(driver.getAddress(), rider.getAddress());
                distanceList.add(distance); // an unorganized list of distances of each rider to the driver
            }
            Collections.sort(distanceList); // sorts the distances

            for(Double dist : distanceList) { // loops through this organized list
                for(Rider rider : r) { // loops through each rider again
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
     */
    public void assignOccupants(List<Driver> driverList) {
        for(Driver driver : driverList) {
            for(Rider r : driver.getPreferences()) {
                if((r.getCurrentCar() == null) && !driver.getCar().isFull() && !betterOption(driver, r, driverList)) {
                    driver.getCar().addOccupant(r);
                }
            }
        }
    }

    private List<Driver> getDrivers() { return drivers; }

    private List<Rider> getRiders() { return riders; }

    private boolean betterOption(Driver driver, Rider rider, List<Driver> driverList) { // if there is a better choice for the rider
        int currentPref = driver.getPreferences().indexOf(rider); // current index(rank) in relation to our specified driver
        for(Driver d : driverList) { // loops through all the drivers
            int otherPrefs = d.getPreferences().indexOf(rider);
            if(otherPrefs < currentPref) { // if another driver has ranked this rider higher(lower in a literal sense) then our driver's current rank
                return true; // return true that there is a better option so don't add the rider to this specified driver
            }
        }
        return false; // or return false if all other drivers have this rider ranked lower. This driver is the best option for them
    }
}
