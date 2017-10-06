import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Manager {

    private List<Driver> drivers = new ArrayList<>();
    private List<Rider> riders = new ArrayList<>();
    private MapsAPI maps = new MapsAPI();

    public void loadData(File f) { // method to load information from text file. to be changed later
       System.out.println(maps.getDistance("University of Washington", "Seattle Pacific University") + " km");

    }

    private List<Driver> getDrivers() { return drivers; }

    private List<Rider> getRiders() { return riders; }
}
