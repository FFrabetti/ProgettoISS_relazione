package it.unibo.test.ra;

import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import it.unibo.ctxReqAnalysis.MainCtxReqAnalysis;
import it.unibo.finalTask2018.ra.temperatureAdapter;
import it.unibo.qactor.testutils.QActorTestUtils;
import it.unibo.qactors.akka.QActor;

public class TemperatureAgentTest {
	
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
	public void temperatureEmissionTest() {
		try {
			Thread.sleep(5000 + 1000);
			Assert.assertTrue(QActorTestUtils.isEventReceived(ralogger, "temperature", "temperature(" + temperatureAdapter.getCurrentTemp() + ")"));
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
}
