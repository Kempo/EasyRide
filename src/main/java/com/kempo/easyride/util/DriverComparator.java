package com.kempo.easyride.util;


import com.kempo.easyride.model.Driver;

import java.util.Comparator;

public class DriverComparator implements Comparator<Driver> {

    // 1 = greater than
    // -1 = less than
    @Override
    public int compare(Driver driver1, Driver driver2) {

        if(driver1.getDistanceTo() > driver2.getDistanceTo()) {
            return 1;
        }else {
            if (driver1.getDistanceTo() <= driver2.getDistanceTo()) {
                return -1;
            }
        }

        return 0; // if the two objects are equal
    }


    @Override
    public boolean equals(Object obj) {
        return false;
    }
}
