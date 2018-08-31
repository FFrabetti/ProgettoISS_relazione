package it.unibo.test.ra;

import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import it.unibo.ctxReqAnalysis.MainCtxReqAnalysis;
import it.unibo.qactor.testutils.QActorTestUtils;
import it.unibo.qactors.akka.QActor;

public class HumanOpApplTest {

	private static QActor ralogger;
	private static QActor humanoperatorra;
	
	@BeforeClass
	public static void setUpBeforeClass() {
		try {
			MainCtxReqAnalysis.initTheContext();

			ralogger = QActorTestUtils.waitForQActorToStart("ralogger");
			humanoperatorra = QActorTestUtils.waitForQActorToStart("humanoperatorra");
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public void robotCmdEmissionTest() {
		try {
			Thread.sleep(2000);
			Assert.assertTrue(QActorTestUtils.isEventReceived(ralogger, "robotCmd", "moveRobot(w(X))"));
			Assert.assertTrue(QActorTestUtils.isEventReceived(ralogger, "robotCmd", "moveRobot(h(X))"));
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	@Test
	public void lightCmdEmissionTest() {
		try {
			Thread.sleep(5000);
			Assert.assertTrue(QActorTestUtils.isEventReceived(ralogger, "lightCmd", "lightCmd(on)"));
			Assert.assertTrue(QActorTestUtils.isEventReceived(ralogger, "lightCmd", "lightCmd(off)"));
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public void cmdMsgEmissionTest() {
		try {
			QActorTestUtils.sendMsg(humanoperatorra, "applra", "cmd", "cmd(d(0))");
			Thread.sleep(2000);
			Assert.assertTrue(QActorTestUtils.isEventReceived(ralogger, "robotCmd", "moveRobot(d(X))"));
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
}
