package com.kempo.easyride.model;

public class Unclassified {
    private final String line;
    private final String reason;
    public Unclassified(String l, String r) {
        line = l;
        reason = r;
    }

    public String getLine() {
        return line;
    }
    public String getReason() {
        return reason;
    }
}
