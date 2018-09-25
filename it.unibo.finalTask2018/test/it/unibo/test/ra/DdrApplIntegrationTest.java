package it.unibo.test.ra;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import it.unibo.ctxDdr.MainCtxDdr;
import it.unibo.ctxRaIntegrator.MainCtxRaIntegrator;
import it.unibo.ctxReqAnalysis.MainCtxReqAnalysis;
import it.unibo.finalTask2018.ra.robotAdapter;
import it.unibo.qactor.testutils.QATesting;
import it.unibo.qactors.QActorContext;
import it.unibo.qactors.akka.QActor;

public class DdrApplIntegrationTest extends QATesting {

	private static QActor ddrlogger;
	
	private static Process integrator;
	private static Process reqanal;
	private static QActorContext ctxDdr;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ctxDdr = MainCtxDdr.initTheContext();
		ddrlogger = waitForQActorToStart("ddrlogger");
		
		reqanal = execMain(MainCtxReqAnalysis.class);
		System.out.println("Waiting for MainCtxDdr and MainCtxReqAnalysis to start...");
		Thread.sleep(20000);
		integrator = execMain(MainCtxRaIntegrator.class);
		Thread.sleep(10000);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("Terminating QActor system MainCtxDdr");
		Thread.sleep(5000);
		ctxDdr.terminateQActorSystem();
		Thread.sleep(5000);
		
		stopProcess(integrator);
		System.out.println("MainCtxRaIntegrator stopped");
		
		stopProcess(reqanal);
		System.out.println("MainCtxReqAnalysis stopped");
	}
	
	@Test
	public void cmdReceptionTest() throws Exception {
		sendMsg(ddrlogger, "applra", "cmd", "cmd(w(2))");
		Thread.sleep(2000);
		assertTrue(isEventReceived(ddrlogger, "robotCmd", "moveRobot(w(2))"));
		assertEquals("forward", robotAdapter.getStatus());
	}

	@Test
	public void lightCmdEmissionTest() throws Exception {
		sendMsg(ddrlogger, "applra", "cmd", "cmd(h(2))");
		Thread.sleep(2000);
		assertTrue(isEventReceived(ddrlogger, "lightCmd", "lightCmd(off)"));
	}
	
}
