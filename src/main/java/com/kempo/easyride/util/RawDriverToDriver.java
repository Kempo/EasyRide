package com.kempo.easyride.util;

import com.kempo.easyride.model.Car;
import com.kempo.easyride.model.Driver;
import com.kempo.easyride.model.RawDriver;

import java.util.function.Function;

public class RawDriverToDriver implements Function<RawDriver, Driver> {

    private RawDriverToDriver()
    {

    }

    public static final RawDriverToDriver INSTANCE = new RawDriverToDriver();

    @Override
    public Driver apply(RawDriver rawDriver) {
        Car c = new Car(rawDriver.getSpaces());
        Driver result = new Driver(rawDriver.getName(), rawDriver.getAddress(), c);
        return result;
    }
}
