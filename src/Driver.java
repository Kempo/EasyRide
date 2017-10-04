public class Driver {
    private String address;
    private String name;
    private Car car;
    public Driver(String n, String a, Car c) {
        address = a;
        name = n;
        car = c;
    }

    public String getAddress() { return address; }
    public String getName() { return name; }
    public Car getCar() { return car; }
}
