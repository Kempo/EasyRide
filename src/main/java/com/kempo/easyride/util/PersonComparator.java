package com.kempo.easyride.util;

import com.kempo.easyride.model.Person;

import java.util.Comparator;

public class PersonComparator implements Comparator<Person> {

    @Override
    public int compare(final Person p1, final Person p2) {
        if(p1.getDistanceTo() > p2.getDistanceTo()) {
            return 1;
        }else {
            if (p1.getDistanceTo() <= p2.getDistanceTo()) {
                return -1;
            }
        }

        return 0; // if the two objects are equal
    }
}
