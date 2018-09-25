package it.unibo.test.crs;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import alice.tuprolog.SolveInfo;
import it.unibo.ctxSwag4.MainCtxSwag4;
import it.unibo.qactor.testutils.QATesting;
import it.unibo.qactors.akka.QActor;

public class SwAgentObstacleAvTest extends QATesting {

	private static QActor swag;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		MainCtxSwag4.initTheContext();
		swag = waitForQActorToStart("swag4");
		Thread.sleep(2000);
	}

	@After
	public void afterTest() throws Exception {
		String currState = getLastState();
		swag.solveGoal("removeLogState");
		
		if(currState != null) {
//			System.out.println("Current state = " + currState);	
			swag.addRule("logstate(" + currState + ",0)");
		}
	}
	
	@Test
	public void avoidMobileTest() throws Exception {
		// cleaning
		simulateObstacle();
		// handleFront
		Thread.sleep(2000);
		// avoidMobile
		// cleaning
		
		assertHistory("[cleaning,handleFront,avoidMobile,cleaning]");
	}
	
	@Test
	public void avoidFixDoorFoundRightTest() throws Exception {
		// cleaning
		simulateObstacle();
		// handleFront
		Thread.sleep(400);
		simulateObstacle();
		// avoidFix
		Thread.sleep(20000);
		// checkDoor
		// doorFound
		// goToPrevLevel
		// goToPrevLevel (riprendo direzione di marcia)
		// cleaning
		
		assertHistory("[cleaning,handleFront,avoidFix,"
				+ "checkDoor,doorFound,goToPrevLevel,goToPrevLevel,cleaning]");
	}
	
	@Test
	public void avoidFixDoorFoundRight2Test() throws Exception {
		// cleaning
		simulateObstacle();
		// handleFront
		Thread.sleep(1200);
		simulateObstacle();
		// avoidFix
		Thread.sleep(400);
		assertEquals("avoidFix", getLastState());
		Thread.sleep(2000);
		
		// il frontSonar deve arrivare in checkDoor
		// se arriva in avoidFix va in failure
		waitForState("checkDoor");
		
		// checkDoor
		simulateObstacle();
		// avoidFix
		// checkDoor
		// doorFound
		// goToPrevLevel
		// goToPrevLevel
		// goToPrevLevel (riprendo direzione di marcia)
		// cleaning
		Thread.sleep(25000);
		
		assertHistory("[cleaning,handleFront,avoidFix,checkDoor,avoidFix,"
			+ "checkDoor,doorFound,goToPrevLevel,goToPrevLevel,goToPrevLevel,cleaning]");
	}
	
	@Test
	public void avoidFixFailureRightTest() throws Exception {
		// cleaning
		simulateObstacle();
		// handleFront
		Thread.sleep(1200);
		simulateObstacle();
		Thread.sleep(400);
		// avoidFix
		assertEquals("avoidFix", getLastState());
		Thread.sleep(5400);
		simulateObstacle();
		Thread.sleep(400);
		// failure
		assertEquals("failure", getLastState());
		// giveUp
		// resumeLastPosition
		// avoidFix
		// checkDoor
		// doorFound
		// goToPrevLevel
		// goToPrevLevel (riprendo direzione di marcia)
		// cleaning
		Thread.sleep(25000);
		
		assertHistory("[cleaning,handleFront,avoidFix,failure,giveUp,"
			+ "resumeLastPosition,avoidFix,checkDoor,doorFound,goToPrevLevel,goToPrevLevel,cleaning]");
	}
	
	@Test
	public void avoidFixGiveUpRightLeftTest() throws Exception {
		String limit = swag.solveGoal("giveUpLimit(L)").getTerm("L").toString();
		swag.replaceRule("giveUpLimit(_)","giveUpLimit(1)");
		
		// cleaning
		simulateObstacle();
		// handleFront
		Thread.sleep(1200);
		simulateObstacle();
		// avoidFix
		Thread.sleep(800);
		// giveUp
		// resumeLastPosition
		// avoidFix
		// giveUp
		// (init)
		// cleaning
		Thread.sleep(20000);
		
		swag.replaceRule("giveUpLimit(_)","giveUpLimit(" + limit + ")");
		
		assertHistory("[cleaning,handleFront,avoidFix,giveUp,"
			+ "resumeLastPosition,avoidFix,giveUp,cleaning]");
	}
	
	// utilities
	private void simulateObstacle() {
		System.out.println("TEST: emitting frontSonar");
		swag.emit("frontSonar", "sonar(2)");
	}
	
	private String getLastState() throws Exception {
		SolveInfo si = swag.solveGoal("stateHistory(L), length(L,N), I is N-1, logstate(S,I)");
		return si.isSuccess() ? si.getTerm("S").toString() : null;
	}

	// assertEquals: in caso di fallimento fornisce quello che sarebbe dovuto essere
	private void assertHistory(String expected) throws Exception {
		assertEquals(expected, swag.solveGoal("stateHistory(H)").getTerm("H").toString());
	}
	
	private void waitForState(String state) throws Exception {
		int count = 0;
		do {
			count++;
			Thread.sleep(400);
		} while(!swag.solveGoal("logstate(" + state + ",_)").isSuccess() && count<10);
	}
}
