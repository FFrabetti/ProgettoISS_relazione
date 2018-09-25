package it.unibo.test.ra;

import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import it.unibo.ctxReqAnalysis.MainCtxReqAnalysis;
import it.unibo.finalTask2018.ra.clockAdapter;
import it.unibo.qactor.testutils.QATesting;
import it.unibo.qactors.akka.QActor;

public class ClockAgentTest extends QATesting {

	private static QActor ralogger;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		MainCtxReqAnalysis.initTheContext();
		ralogger = waitForQActorToStart("ralogger");
	}

	@Test
	public void clockEmissionTest() throws Exception {
		Thread.sleep(6000 + 1000);
		int[] time = clockAdapter.getCurrentTime();
		assertTrue(isEventReceived(ralogger, "clock", "clock(" + time[0] + "," + time[1] + ")"));
	}

}
