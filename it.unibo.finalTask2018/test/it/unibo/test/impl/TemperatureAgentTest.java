package it.unibo.test.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import it.unibo.ctxAppl.MainCtxAppl;
import it.unibo.qactor.testutils.QATesting;
import it.unibo.qactors.akka.QActor;
import it.unibo.sockutils.tcp.TCPServer;

public class TemperatureAgentTest extends QATesting {

	private static QActor crslogger;
	private static QActor tempagent;

	private static final int SRV_PORT = 6667;
	private static List<String> receivedMsgs = new LinkedList<>();
	private static Thread serverThread;
	private static Process thermoServer;
	private static final String TEMP = "20.20";
	
	private static void setUpMockServer() {
		TCPServer server = new TCPServer(SRV_PORT, req -> {
			receivedMsgs.add(req);
			return TEMP;
		});
		serverThread = server.runOnThread();
	}

	private static void setUpThermoServer() throws Exception {
		thermoServer = Runtime.getRuntime().exec("java -jar ../ThermoServer.jar");
	}

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		setUpMockServer();
//		setUpThermoServer();
		MainCtxAppl.initTheContext();
		crslogger = waitForQActorToStart("crslogger");
		tempagent = waitForQActorToStart("temperatureagent");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		stopProcess(thermoServer);
		if (serverThread != null && serverThread.isAlive())
			serverThread.interrupt();
	}

	@Test
	public void temperatureEmissionTest() throws Exception {
		int period = Integer.parseInt(tempagent.solveGoal("requestPeriod(P)").getVarValue("P").toString());
		Thread.sleep(period + 1000);
		if (serverThread != null)
			assertFalse(receivedMsgs.isEmpty());
		assertTrue(isEventReceived(crslogger, "temperature", "temperature(" + TEMP + ")"));
	}

}
