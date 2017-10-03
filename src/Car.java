import java.util.ArrayList;

public class Car {
    private final int total;
    private int spots; // begins with the total number of spots open, then it will decrease as occupants are added
    private ArrayList<Object> occupants; // begins at 0, increases as occupants are assigned to their driver's car


    public Car(int s, ArrayList<Object> o) { //ArrayList<Object> will be changed later
        spots = s;
        total = s;
        occupants = (ArrayList<Object>)o.clone();
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

    public ArrayList<Object> getOccupants() {
        return occupants;
    }

    public int getSpots() {
        return spots;
    }
}
