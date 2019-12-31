package com.kempo.easyride.model;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class Car {
    private final int total;
    private int spots; // begins with the total number of spots open, then it will decrease as occupants are added

    @Expose
    private List<Rider> occupants; // begins at 0, increases as occupants are assigned to their driver's car

    public Car(int s) {
        spots = s;
        total = s;
        occupants = new ArrayList<>();
    }

    private boolean decreaseSpots() {
        if(spots > 0) {
            spots -= 1;
            return true;
        }else{
            System.out.print("Car is full!");
            return false;
        }
    }

    public int getOpenSpots() {
        return spots;
    }

    public void addOccupant(Rider r) {
        if(!occupants.contains(r)) {
            if(decreaseSpots()) {
                occupants.add(r);
            }else{
                System.out.println("Could not add rider " + r.getName());
            }
        }
    }

    public List<Rider> getOccupants() {
        return occupants;
    }

    public boolean isFull() {
        return (spots == 0);
    }
}
