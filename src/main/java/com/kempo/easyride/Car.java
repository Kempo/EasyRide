package com.kempo.easyride;
import java.util.ArrayList;
import java.util.List;

public class Car {
    private final int total;
    private int spots; // begins with the total number of spots open, then it will decrease as occupants are added
    private List<Rider> occupants; // begins at 0, increases as occupants are assigned to their driver's car

    public Car(int s) {
        spots = s;
        total = s;
        occupants = new ArrayList<>();
    }

    private void decreaseSpots() {
        if(spots > 0) {
            spots -= 1;
        }else{
            System.out.print("Car is full!");
        }
    }

    private void increaseSpots() {
        if(spots < total) {
            spots += 1;
        }
    }

    public void addOccupant(Rider r) {
        if(!occupants.contains(r)) {
            occupants.add(r);
            decreaseSpots();
        }
    }

    public void removeOccupant(Rider r) {
        if(occupants.contains(r)) {
            occupants.remove(r);
            increaseSpots();
        }
    }

    public List<Rider> getOccupants() {
        return occupants;
    }

    public int getSpots() {
        return spots;
    }

    public boolean isFull() {
        return (spots == 0);
    }
}
