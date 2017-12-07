package com.kempo.easyride.util;

public class TestUtility {

    public static String createTestParticipant(String name, String address, String designation, int spots) {
        return name + "\t" + address + "\t" + designation + (designation.equals("driver") ? "\t" + spots : "");
    }
}
