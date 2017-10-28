package com.kempo.easyride.model;
/**
 * Created by dileng on 10/12/17.
 */
public class Person
{
    protected String name;

    protected String address;

    public Person(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public Person() {

    }

    public String getName()
    {
        return name;
    }

    public String getAddress()
    {
        return address;
    }

    @Override
    public String toString()
    {
        return "Name: " + name + ", Address: " + address;
    }
}