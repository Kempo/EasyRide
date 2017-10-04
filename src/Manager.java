import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class Manager {
    private final String KEY = "AIzaSyDjkGSDw_dX7iJhvb5mHu8rotwB0WfJgjk"; // Google Distance Matrix API
    private String destination;
    private String origin;
    private final String URL = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=" + origin + "&destinations=" + destination + "&key=" + KEY;
    private List<Driver> drivers = new ArrayList<Driver>();

    public void loadData(File f) { // method to load information from text file. to be changed later

    }

    private void setOrigin(String s) { //example 'University of Washington' to 'University_Of_Washington'
        origin = s.replaceAll(" ", "_");
    }
    private void setDestination(String s) {
        destination = s.replaceAll(" ", "_");
    }
    private List<Driver> getDrivers() { return drivers; }
}
