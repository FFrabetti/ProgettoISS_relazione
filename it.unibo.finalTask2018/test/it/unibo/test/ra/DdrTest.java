package it.unibo.test.ra;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import it.unibo.ctxDdr.MainCtxDdr;
import it.unibo.finalTask2018.ra.robotAdapter;
import it.unibo.qactor.testutils.QATesting;
import it.unibo.qactors.akka.QActor;

public class DdrTest extends QATesting {

	private static QActor ddrlogger;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		MainCtxDdr.initTheContext();

		ddrlogger = waitForQActorToStart("ddrlogger");
	}

	@Test
	public void moveRobotReceptionTest() throws Exception {
		sendMsg(ddrlogger, "ddr", "moveRobot", "moveRobot(d(0))");
		sleep(2000);
		assertEquals("right", robotAdapter.getStatus());
	}
	
	@Test
	public void robotCmdReceptionTest() {
		ddrlogger.emit("robotCmd", "moveRobot(a(0))");
		sleep(2000);
		assertTrue(isEventReceived(ddrlogger, "robotCmd", "moveRobot(a(0))"));
		assertEquals("left", robotAdapter.getStatus());
	}
	
	@Test
	public void sonarEmissionTest() {
		sleep(5000);
		assertTrue(isEventReceived(ddrlogger, "sonarSensor", "sonar(N,D)"));
		assertTrue(isEventReceived(ddrlogger, "frontSonar", "sonar(D)"));
	}
	
	@Test
	public void lightCmdReceptionTest() {
		// integration test ddr + led/hue lamp
		Assert.fail("Not yet implemented");
	}

}
