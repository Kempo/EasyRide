package com.kempo.easyride.model;

/**
 * Created by dileng on 10/12/17.
 */
public class RawDriver extends Person
{
    private int spaces;

    public RawDriver(final String name, final String address, final int spaces)
    {
        this.name = name;
        this.address = address;
        this.spaces = spaces;
    }

    public int getSpaces()
    {
        return spaces;
    }

    @Override
    public String toString()
    {
        return super.toString() + ", spaces: " + spaces;
    }

}
