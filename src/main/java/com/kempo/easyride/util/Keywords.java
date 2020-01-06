package com.kempo.easyride.util;

public class Keywords {

    public static final String[] DEFAULT_STATE = {"washington", "wa"}; // later on we can add it so that EasyRide accommodates for other regions

    // COLUMNS
    public static final String[]
            NAMES = {"name"},
            ADDRESSES = {"address", "live", "home"},
            SIZE = {"spots", "how many"},
            DESIGNATION = {"how are you"};

    // RESPONSES
    public static final String[]
            RIDER = {"need a ride"},
            DRIVER = {"give a ride", "can drive"};

    public static final String[][] requirements = {NAMES, ADDRESSES, SIZE, DESIGNATION};
}


/**
 * I don't need a ride and I cannot give a ride (null)
 * I don't need a ride and I can give a ride (DRIVER)
 * I don't need a ride (null)
 * I need a ride (RIDER)
 *
 * I cannot give a ride. (null)
 * I can give a ride. (DRIVER)
 *
 * How are you getting to _____?
 * Will you need a ride?
 * Do you need a ride?
 *
 **/