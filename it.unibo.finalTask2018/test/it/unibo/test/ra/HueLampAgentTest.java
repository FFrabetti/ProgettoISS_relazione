package it.unibo.test.ra;

import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import it.unibo.ctxReqAnalysis.MainCtxReqAnalysis;
import it.unibo.finalTask2018.ra.hueAdapter;
import it.unibo.qactor.testutils.QActorTestUtils;
import it.unibo.qactors.akka.QActor;

public class HueLampAgentTest {

	private static QActor huelampagentra;
	
	@BeforeClass
	public static void setUpBeforeClass() {
		try {
			MainCtxReqAnalysis.initTheContext();

			huelampagentra = QActorTestUtils.waitForQActorToStart("huelampagentra");
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public void lightCmdReceptionTest() {
		try {
			huelampagentra.emit("lightCmd", "lightCmd(on)");
			Thread.sleep(1000);
			Assert.assertEquals("on", hueAdapter.getCurrentState());
			
			huelampagentra.emit("lightCmd", "lightCmd(off)");
			Thread.sleep(1000);
			Assert.assertEquals("off", hueAdapter.getCurrentState());

			huelampagentra.emit("lightCmd", "lightCmd(appositamenteSbagliato)");
			Thread.sleep(1000);
			Assert.assertEquals("off", hueAdapter.getCurrentState());
			
			huelampagentra.emit("lightCmd", "lightCmd(blink)");
			Thread.sleep(1000);
			Assert.assertEquals("blink", hueAdapter.getCurrentState());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

}
