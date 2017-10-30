package com.kempo.easyride.model;

import java.util.Comparator;

public class DistanceComparator implements Comparator<Rider> {


    // 1 = greater than
    // -1 = less than
    @Override
    public int compare(Rider rider1, Rider rider2) {

        if(rider1.getDistanceTo() > rider2.getDistanceTo()) {
            return 1;
        }else {
            if (rider1.getDistanceTo() < rider2.getDistanceTo()) {
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
