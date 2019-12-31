package com.kempo.easyride.model;

import com.google.gson.annotations.Expose;

/**
 * Created by dileng on 10/12/17.
 */
public class Person
{
    @Expose
    protected String name;

    @Expose
    protected String address;

    protected double distance;

    public Person(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public String getName()
    {
        return name;
    }

    public String getAddress()
    {
        return address;
    }

    public void setDistanceTo(double d) { distance = d; }

    public double getDistanceTo() {
        return distance;
    }

    public boolean isIdentical(Person p) {
        return this.name.equals(p.name) && this.address.equals(p.address);
    }

    @Override
    public String toString()
    {
        return "Name: " + name + ", Address: " + address;
    }
}