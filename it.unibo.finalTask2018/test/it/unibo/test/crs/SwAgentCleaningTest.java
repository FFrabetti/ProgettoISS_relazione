package it.unibo.test.crs;

import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import it.unibo.ctxSwag3.MainCtxSwag3;
import it.unibo.qactor.testutils.QATesting;
import it.unibo.qactors.akka.QActor;

public class SwAgentCleaningTest extends QATesting {
	
	private static QActor swag;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		MainCtxSwag3.initTheContext();
		
		swag = waitForQActorToStart("swag3");
	}

	@Test
	public void floorCleaningTest() throws Exception {
		// startCleaning
		simulateWall();
		// leftTurn
		Thread.sleep(5000);
		// backCleaning
		simulateWall();
		// rightTurn
		Thread.sleep(5000);
		// forwardCleaning
		simulateSonar2(20);
		
		// leftTurn
		Thread.sleep(5000);
		// backCleaning
		simulateWall();
		// rightTurn
		Thread.sleep(5000);
		// forwardCleaning
		simulateSonar2(2);
		// end
		Thread.sleep(400);
		
		assertTrue(swag.solveGoal("stateHistory(["
				+ "startCleaning, leftTurn, backCleaning, rightTurn, forwardCleaning,"
				+ "leftTurn, backCleaning, rightTurn, forwardCleaning, end])").isSuccess());
	}

	// utilities
	private void simulateWall() throws Exception {
		Thread.sleep(2000); // tempo di percorrenza della larghezza della stanza
		swag.emit("frontSonar", "sonar(2)");
	}
	
	private void simulateSonar2(int dist) throws Exception {
		Thread.sleep(2000); // tempo di percorrenza della larghezza della stanza
		swag.emit("sonarSensor", "sonar(sonar2," + dist + ")");
	}
	
}
