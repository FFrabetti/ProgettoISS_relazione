package it.unibo.test.ra;

import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import it.unibo.ctxReqAnalysis.MainCtxReqAnalysis;
import it.unibo.qactor.testutils.QATesting;
import it.unibo.qactors.akka.QActor;

public class HumanOpApplTest extends QATesting {

	private static QActor ralogger;
	private static QActor humanoperatorra;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		MainCtxReqAnalysis.initTheContext();

		ralogger = waitForQActorToStart("ralogger");
		humanoperatorra = waitForQActorToStart("humanoperatorra");
	}

	@Test
	public void robotCmdEmissionTest() {
		sleep(2000);
		assertTrue(isEventReceived(ralogger, "robotCmd", "moveRobot(w(X))"));
		assertTrue(isEventReceived(ralogger, "robotCmd", "moveRobot(h(X))"));
	}
	
	@Test
	public void lightCmdEmissionTest() {
		sleep(5000);
		assertTrue(isEventReceived(ralogger, "lightCmd", "lightCmd(on)"));
		assertTrue(isEventReceived(ralogger, "lightCmd", "lightCmd(off)"));
	}

	@Test
	public void cmdMsgEmissionTest() throws Exception {
		sendMsg(humanoperatorra, "applra", "cmd", "cmd(d(0))");
		sleep(2000);
		assertTrue(isEventReceived(ralogger, "robotCmd", "moveRobot(d(X))"));
	}
	
}
