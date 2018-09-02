package it.unibo.test.ra;

import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import it.unibo.ctxReqAnalysis.MainCtxReqAnalysis;
import it.unibo.finalTask2018.ra.temperatureAdapter;
import it.unibo.qactor.testutils.QATesting;
import it.unibo.qactors.akka.QActor;

public class TemperatureAgentTest extends QATesting {
	
	private static QActor ralogger;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		MainCtxReqAnalysis.initTheContext();

		ralogger = waitForQActorToStart("ralogger");
	}

	@Test
	public void temperatureEmissionTest() throws Exception {
		Thread.sleep(5000 + 1000);
		assertTrue(isEventReceived(ralogger, "temperature", "temperature(" + temperatureAdapter.getCurrentTemp() + ")"));
	}
	
}
