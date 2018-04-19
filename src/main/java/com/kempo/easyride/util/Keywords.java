package com.kempo.easyride.util;

public class Keywords {

    public static final String[] DEFAULT_STATE = {"washington", "wa"}; // later on we can add it so that EasyRide accommodates for other regions

    public static final String[]
            NAMES = {"name"},
            ADDRESSES = {"address", "live"},
            SIZE = {"spots", "size", "car"};

    private static final String[]
            RIDERYES = {""},
            RIDERNO = {""},
            DRIVER = {""};

    public static final String[][] DESIGNATIONS = {RIDERYES, RIDERNO, DRIVER};

    public static final String[][] requirements = {NAMES, ADDRESSES, SIZE, RIDERYES, RIDERNO, DRIVER};
}
