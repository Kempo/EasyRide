import java.util.ArrayList;

public class Rider {
    private String name; // name of the rider
    private String address; // his/her address
    private ArrayList<Car> preferences = new ArrayList<>(); // the list of cars that they prefer (ordered by distance?)
    private Car current; // their current car

    public Rider(String n, String a) {
        name = n;
        address = a;
    }

    public ArrayList<Car> getPreferences() {
        return preferences;
    }

    public Car getCurrentCar() {
        return current;
    }

    public void setCar(Car c) {
        current = c;
    }
}
