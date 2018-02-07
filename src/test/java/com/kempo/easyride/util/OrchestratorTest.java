package com.kempo.easyride.util;

import com.kempo.easyride.application.Orchestrator;
import com.kempo.easyride.application.RideAssigner;
import com.kempo.easyride.model.AssignedRides;
import com.kempo.easyride.model.RawParticipants;
import junit.framework.Assert;
import junit.framework.TestCase;

public class OrchestratorTest extends TestCase {
    private RideParser parser = new RideParser();
    private static Orchestrator orchestrator = new Orchestrator(new RideAssigner());
    private String request = TestUtility.createTestParticipant("Aaron", "Space Needle", "rider", 0)
            + "\n" + TestUtility.createTestParticipant("Ted", "Seattle University", "driver", 2)
            + "\n" + TestUtility.createTestParticipant("Connor", "New York", "driver", 2)
            + "\n" + TestUtility.createTestParticipant("Nick", "Boston", "rider", 0)
            + "\n" + TestUtility.createTestParticipant("Tod", "San Francisco", "rider",0)
            + "\n" + "hello test     hi     tes  test   whoo    test1"
            + "\n" + TestUtility.createTestParticipant("Bob", "Seattle University", "rider", 0)
            + "\n" + TestUtility.createTestParticipant("Lamar", "Seattle University", "rider", 0);


    public void testOrchestratorWithTextInput() {
        final RawParticipants participants = parser.parseInitialRequestThroughTSV(request);
        final AssignedRides result = orchestrator.orchestrateRides(participants);
        Assert.assertEquals(2, result.getDrivers().size());
        Assert.assertEquals(1, result.getUnassignedRiders().size());
        Assert.assertEquals(true, (result.getUnparseable().length() > 0));
        Assert.assertEquals("Lamar", result.getDrivers().get(0).getCar().getOccupants().get(0).getName());
        Assert.assertEquals("Connor", result.getDrivers().get(1).getName());
    }
}
