package com.kempo.easyride.application;

import com.kempo.easyride.model.*;
import com.kempo.easyride.util.RawDriverToDriver;

import java.util.List;
import java.util.stream.Collectors;

/**
 * takes in the RawParticipants list, transforms it to a format that can be
 * read by the RideAssigner, and outputs the result of the RiderAssigner
 */
public class Orchestrator {

    private RideAssigner rideAssigner;

    public Orchestrator(final RideAssigner rideAssigner)
    {
        this.rideAssigner = rideAssigner;
    }

    public AssignedRides orchestrateRides(final RawParticipants rawParticipants)
    {
        final List<RawDriver> rawDrivers = rawParticipants.getDrivers();
        final List<Driver> drivers = rawDrivers.stream().map(RawDriverToDriver.INSTANCE).collect(Collectors.toList());
        final List<Rider> riders = rawParticipants.getRiders();

        rideAssigner.readData(drivers, riders);

        System.out.println("about to assign occupants: " );
        AssignedRides assignedRides = rideAssigner.assignOccupants(drivers, riders);
        System.out.println("documents assigned!");
        return assignedRides;
    }
}
