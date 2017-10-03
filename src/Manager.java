import java.io.File;
import java.util.HashMap;


public class Manager {
    private final String KEY = "AIzaSyDjkGSDw_dX7iJhvb5mHu8rotwB0WfJgjk"; // Google Distance Matrix API
    private String destination;
    private String origin;
    private final String URL = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=" + origin + "&destinations=" + destination + "&key=" + KEY;
    private HashMap<String, Car> drivers = new HashMap<>(); // address, car information (# of spots, current occupants)

    public HashMap<String, Car> getDrivers() {
        return drivers;
    }

    public void loadData(File f) { // method to load information from text file. to be changed later

    }

    private void setOrigin(String s) { //example 'University of Washington' to 'University_Of_Washington'
        origin = s.replaceAll(" ", "_");
    }
    private void setDestination(String s) {
        destination = s.replaceAll(" ", "_");
    }
}
