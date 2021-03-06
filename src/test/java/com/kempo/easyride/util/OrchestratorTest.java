package com.kempo.easyride.util;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.kempo.easyride.application.Orchestrator;
import com.kempo.easyride.application.RideAssigner;
import com.kempo.easyride.google.SheetsAPI;
import com.kempo.easyride.model.AssignedRides;
import com.kempo.easyride.model.Driver;
import com.kempo.easyride.model.RawParticipants;
import com.kempo.easyride.model.Rider;
import junit.framework.Assert;
import junit.framework.TestCase;

public class OrchestratorTest extends TestCase {
    private RideParser parser = new RideParser();
    private Orchestrator orchestrator = new Orchestrator(new RideAssigner());

    public void testWithTextInputAndDriversWithLimitedSpots() {
        String request = "unparseable text"
                + "\n" + TestUtility.createTestParticipant("Tim", "Tacoma WA", "driver", 1) // driver
                + "\n" + TestUtility.createTestParticipant("Eric", "Seattle WA", "driver", 5) // driver
                + "\n" + TestUtility.createTestParticipant("Eugene", "Seattle, WA", "rider", 0)
                + "\n" + TestUtility.createTestParticipant("Nathan", "Tacoma, WA", "rider", 0)
                + "\n" + TestUtility.createTestParticipant("Matthew", "Olympia, WA", "rider", 0)
                + "\n" + TestUtility.createTestParticipant("Lamar", "Olympia, WA", "rider", 0)
                + "\n" + TestUtility.createTestParticipant("Tom", "Seattle University", "rider", 0)
                + "\n" + TestUtility.createTestParticipant("Rick", "Olympia, WA", "driver", 1); // driver
        final RawParticipants participants = parser.parseInitialRequestThroughTSV(request);
        final AssignedRides result = orchestrator.orchestrateRides(participants);

        Assert.assertEquals(1, participants.getUnclassifieds().size());
        Assert.assertEquals(1, result.getDrivers().get(0).getCar().getOccupants().size());
        Assert.assertEquals(3, result.getDrivers().get(1).getCar().getOccupants().size());
        Assert.assertEquals(1, result.getDrivers().get(2).getCar().getOccupants().size());
    }

    public void testWithTextInputAndTopPreferences() {
        String request =
                TestUtility.createTestParticipant("Tim", "Tacoma WA", "driver", 5) // driver
                + "\n" + TestUtility.createTestParticipant("Eric", "Seattle WA", "driver", 5) // driver
                + "\n" + TestUtility.createTestParticipant("Eugene", "Seattle, WA", "rider", 0)
                + "\n" + TestUtility.createTestParticipant("Nathan", "Tacoma, WA", "rider", 0)
                + "\n" + TestUtility.createTestParticipant("Matthew", "Olympia, WA", "rider", 0)
                + "\n" + TestUtility.createTestParticipant("Lamar", "Olympia, WA", "rider", 0)
                + "\n" + TestUtility.createTestParticipant("Tom", "Seattle University", "rider", 0)
                + "\n" + TestUtility.createTestParticipant("Rick", "Olympia, WA", "driver", 5); // driver

        final RawParticipants participants = parser.parseInitialRequestThroughTSV(request);
        final AssignedRides result = orchestrator.orchestrateRides(participants);
        Assert.assertEquals(true, result.getDrivers().get(0).getCar().getOccupants().get(0).getName().equals("Nathan"));
        Assert.assertEquals(1, result.getDrivers().get(0).getCar().getOccupants().size());
        Assert.assertEquals(2, result.getDrivers().get(1).getCar().getOccupants().size());
        Assert.assertEquals(2, result.getDrivers().get(2).getCar().getOccupants().size());
    }


}
