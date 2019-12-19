package com.kempo.easyride.model;
/**
 * Created by dileng on 10/12/17.
 */
public class Person
{
    protected String name;

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