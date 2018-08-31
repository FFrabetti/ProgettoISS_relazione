package it.unibo.test.ra;

import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import it.unibo.ctxReqAnalysis.MainCtxReqAnalysis;
import it.unibo.finalTask2018.ra.clockAdapter;
import it.unibo.qactor.testutils.QActorTestUtils;
import it.unibo.qactors.akka.QActor;

public class ClockAgentTest {

	private static QActor ralogger;
	
	@BeforeClass
	public static void setUpBeforeClass() {
		try {
			MainCtxReqAnalysis.initTheContext();

			ralogger = QActorTestUtils.waitForQActorToStart("ralogger");
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public void clockEmissionTest() {
		try {
			Thread.sleep(6000 + 1000);
			int[] time = clockAdapter.getCurrentTime();
			Assert.assertTrue(QActorTestUtils.isEventReceived(ralogger, "clock", "clock(" + time[0] + "," + time[1] + ")"));
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

}
