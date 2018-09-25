package it.unibo.test.crs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import alice.tuprolog.NoSolutionException;
import alice.tuprolog.UnknownVarException;
import it.unibo.ctxSwag1.MainCtxSwag1;
import it.unibo.qactor.testutils.QATesting;
import it.unibo.qactors.akka.QActor;

public class SwAgentStartStopEndTest extends QATesting {

	private static QActor swag;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		MainCtxSwag1.initTheContext();
		swag = waitForQActorToStart("swag1");
	}

	@After
	public void afterTest() {
		swag.solveGoal("removeLogMsg");
	}
	
	@Test
	public void externalCmdReceptionTest() throws Exception {
		swag.emit("alarm", "usercmd(testcmd)");
		Thread.sleep(1000);
		assertTrue(isMsgReceived(swag, "externalcmd", "usercmd(testcmd)"));
	}
	
	@Test
	public void cleanCmdTooLateSonarTest() throws Exception {
		swag.emit("alarm", "usercmd(clean)");
		Thread.sleep(2000);
		swag.emit("sonarSensor", "sonar(sonar1,1)");
		// too late
		assertEquals("init", getCurrentState());
		
		swag.emit("alarm", "usercmd(clean)");
		Thread.sleep(1000); // sonarSensor lost?
		swag.emit("alarm", "usercmd(halt)");
		Thread.sleep(1000);
	}
	
	@Test
	public void cleanCmdNotCloseToSonarTest() throws Exception {
		swag.emit("alarm", "usercmd(clean)");
		Thread.sleep(400);
		swag.emit("sonarSensor", "sonar(sonar1,10)");
		Thread.sleep(2000);
		// not close to sonar1
		assertEquals("init", getCurrentState());
	}
	
	@Test
	public void cleanHaltCmdTest() throws Exception {
		swag.emit("alarm", "usercmd(clean)");
		Thread.sleep(400);
		swag.emit("sonarSensor", "sonar(sonar1,1)");
		Thread.sleep(400);
		// ok clean
		assertEquals("cleaning", getCurrentState());
		assertTrue(isMsgReceived(swag, "swagmsg", "cmd(clean)"));
		
		swag.emit("alarm", "usercmd(halt)");
		Thread.sleep(2000);
		// halted
		assertEquals("init", getCurrentState());
		assertTrue(isMsgReceived(swag, "externalcmd", "usercmd(halt)"));
	}

	@Test
	public void endTest() throws Exception {
		swag.emit("alarm", "usercmd(clean)");
		Thread.sleep(400);
		swag.emit("sonarSensor", "sonar(sonar1,1)");
		Thread.sleep(400);
		// ok
		assertEquals("cleaning", getCurrentState());
		assertTrue(isMsgReceived(swag, "swagmsg", "cmd(clean)"));
		
		swag.emit("sonarSensor", "sonar(sonar2,10)");
		Thread.sleep(2000);
		// not close to sonar2
		assertEquals("cleaning", getCurrentState());
		
		swag.emit("sonarSensor", "sonar(sonar2,1)");
		Thread.sleep(400);
		// ok end
		assertEquals("init", getCurrentState());
		assertTrue(isMsgReceived(swag, "swagmsg", "cmd(halt)"));
	}
	
	// utilities
	private String getCurrentState() throws NoSolutionException, UnknownVarException {
		return swag.solveGoal("currentState(S)").getTerm("S").toString();
	}

}
