import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import it.unibo.ctxRealRobotRasp.MainCtxRealRobotRasp;
import it.unibo.qactor.testutils.QActorTestUtils;
import it.unibo.qactors.akka.QActor;

public class RealRobotTest {

	private static QActor realrobotrasp;
	private static QActor ledagent;
	private static QActor realrobottest;

	@BeforeClass
	public static void setUp() {
		// RUN: raspberry\pyServer\server_mock_no_arduino.py
		
		try {
			MainCtxRealRobotRasp.initTheContext();

			realrobotrasp = QActorTestUtils.waitForQActorToStart("realrobotrasp");
			ledagent = QActorTestUtils.waitForQActorToStart("ledagent");
			realrobottest = QActorTestUtils.waitForQActorToStart("realrobottest");
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public void movCmdReceptionTest() {
		try {
			realrobottest.emit("ctrlEvent", "ctrlEvent(robot,r1,w(0))");
			Thread.sleep(1000);
			Assert.assertTrue(QActorTestUtils.isMsgReceived(realrobotrasp, "moveRobot", "moveRobot(w(0))"));
			
			realrobottest.emit("ctrlEvent", "ctrlEvent(robot,r1,a(0))");
			Thread.sleep(1000);
			Assert.assertTrue(QActorTestUtils.isMsgReceived(realrobotrasp, "moveRobot", "moveRobot(a(0))"));
			
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public void lightCmdReceptionTest() {
		try {
			realrobottest.emit("lightCmd", "lightCmd(on)");
			Thread.sleep(1000);
			Assert.assertTrue(QActorTestUtils.isEventReceived(ledagent, "lightCmd", "lightCmd(on)"));
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public void frontSonarEmissionTest() {
		try {
			Thread.sleep(10000);
			Assert.assertTrue(QActorTestUtils.isEventReceived(realrobottest, "frontSonar", "sonar(D)"));
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

}
