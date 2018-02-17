package com.kempo.easyride.util;

import com.kempo.easyride.application.RideAssigner;
import com.kempo.easyride.model.Car;
import com.kempo.easyride.model.Driver;
import com.kempo.easyride.model.Rider;
import junit.framework.Assert;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RiderAssignerTest extends TestCase {
    public RideAssigner rideAssigner = new RideAssigner();


    public void testBasicPreferencesList() {
        List<Driver> driverList = new ArrayList<>();
        List<Rider> riderList = new ArrayList<>();
        Driver[] drivers = {new Driver("Tim", "Tacoma, WA", new Car(5) ), new Driver("Eric", "Seattle, WA", new Car(5)), new Driver("Rick", "Olympia, WA", new Car(5))};
        Rider[] riders = {new Rider("Eugene", "Seattle, WA"), new Rider("Nathan", "Tacoma, WA"), new Rider("Matthew", "Olympia, WA"), new Rider("Tom", "Seattle University")};
        driverList.addAll(Arrays.asList(drivers));
        riderList.addAll(Arrays.asList(riders));

        rideAssigner.loadPreferences(driverList, riderList);

        Assert.assertEquals("Tim", riderList.get(riderList.indexOf(riders[1])).getRiderPreferences().get(0).getName());
        Assert.assertEquals("Rick", riderList.get(riderList.indexOf(riders[2])).getRiderPreferences().get(0).getName());
    }
}
