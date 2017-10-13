package com.kempo.easyride.model;
import java.util.ArrayList;
import java.util.List;

public class Car {
    private final int total;
    private int spots; // begins with the total number of spots open, then it will decrease as occupants are added
    private List<Rider> occupants; // begins at 0, increases as occupants are assigned to their driver's car


    public Car(int s, ArrayList<Rider> o) {
        spots = s;
        total = s;
        occupants = (ArrayList<Rider>)o.clone();
    }

    public void decreaseSpot() {
        if(spots > 0) {
            spots -= 1;
        }else{
            System.out.print("Car is full!");
        }
    }

    public void increaseSpot() {
        if(spots < total) {
            spots += 1;
        }
    }

    public List<Rider> getOccupants() {
        return occupants;
    }

    public int getSpots() {
        return spots;
    }
}
