package it.unibo.test.ra;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import it.unibo.ctxReqAnalysis.MainCtxReqAnalysis;
import it.unibo.finalTask2018.ra.hueAdapter;
import it.unibo.qactor.testutils.QATesting;
import it.unibo.qactors.akka.QActor;

public class HueLampAgentTest extends QATesting {

	private static QActor huelampagentra;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		MainCtxReqAnalysis.initTheContext();

		huelampagentra = waitForQActorToStart("huelampagentra");
	}

	@Test
	public void lightCmdReceptionTest() throws Exception {
		huelampagentra.emit("lightCmd", "lightCmd(on)");
		Thread.sleep(1000);
		assertEquals("on", hueAdapter.getCurrentState());
		
		huelampagentra.emit("lightCmd", "lightCmd(off)");
		Thread.sleep(1000);
		assertEquals("off", hueAdapter.getCurrentState());

		huelampagentra.emit("lightCmd", "lightCmd(wrong)");
		Thread.sleep(1000);
		assertEquals("off", hueAdapter.getCurrentState());
		
		huelampagentra.emit("lightCmd", "lightCmd(blink)");
		Thread.sleep(1000);
		assertEquals("blink", hueAdapter.getCurrentState());
	}

}
