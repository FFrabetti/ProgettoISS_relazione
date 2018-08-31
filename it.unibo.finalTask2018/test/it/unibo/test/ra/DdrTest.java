package it.unibo.test.ra;

import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import it.unibo.ctxDdr.MainCtxDdr;
import it.unibo.finalTask2018.ra.robotAdapter;
import it.unibo.qactor.testutils.QActorTestUtils;
import it.unibo.qactors.akka.QActor;

public class DdrTest {

	private static QActor ddrlogger;
	
	@BeforeClass
	public static void setUpBeforeClass() {
		try {
			MainCtxDdr.initTheContext();

			ddrlogger = QActorTestUtils.waitForQActorToStart("ddrlogger");
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public void robotCmdReceptionTest() {
		try {
			ddrlogger.emit("robotCmd", "moveRobot(a(0))");
			Thread.sleep(2000);
			Assert.assertTrue(QActorTestUtils.isEventReceived(ddrlogger, "robotCmd", "moveRobot(a(X))"));
			Assert.assertEquals("left", robotAdapter.getStatus());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	@Test
	public void lightCmdReceptionTest() {
		// sarebbe un integration test con ddr + led o ddr + hue lamp
		fail("Not yet implemented");
	}

	@Test
	public void sonarEmissionTest() {
		try {
			Thread.sleep(5000);
			Assert.assertTrue(QActorTestUtils.isEventReceived(ddrlogger, "sonarSensor", "sonar(N,D)"));
			Assert.assertTrue(QActorTestUtils.isEventReceived(ddrlogger, "frontSonar", "sonar(D)"));
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
}
