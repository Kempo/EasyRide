package com.kempo.easyride.util;

import com.kempo.easyride.application.Orchestrator;
import com.kempo.easyride.application.RideAssigner;
import com.kempo.easyride.model.AssignedRides;
import com.kempo.easyride.model.RawParticipants;
import junit.framework.TestCase;

public class OrchestratorTest extends TestCase {
    private RideParser parser = new RideParser();
    private static Orchestrator orchestrator = new Orchestrator(new RideAssigner());
    private String request = TestUtility.createTestParticipant("Aaron", "Space Needle", "rider", 0)
            + "\n" + TestUtility.createTestParticipant("Ted", "Seattle University", "driver", 6)
            + "\n" + TestUtility.createTestParticipant("Connor", "New York", "driver", 6)
            + "\n" + TestUtility.createTestParticipant("Nick", "Boston", "rider", 0)
            + "\n" + TestUtility.createTestParticipant("Tod", "San Francisco", "rider",0);


    public void testOrchestratorWithTextInput() {
        final RawParticipants participants = parser.parseInitialRequest(request);
        final AssignedRides result = orchestrator.orchestrateRides(participants);
        System.out.println(result.toString());
    }
}
