package it.unibo.test.crs;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import it.unibo.ctxAppl.MainCtxAppl;
import it.unibo.qactor.testutils.QActorTestUtils;
import it.unibo.qactors.akka.QActor;
import it.unibo.sockutils.tcp.TCPServer;

public class TemperatureAgentTest {

	private static QActor crslogger;
	private static QActor temperatureagent;

	private static final int SRV_PORT = 6667;
	private static List<String> receivedMsgs = new LinkedList<>();
	private static Thread serverThread;
	private static Process process;

	private static void setUpMockServer() {
		TCPServer server = new TCPServer(SRV_PORT, req -> {
			receivedMsgs.add(req);
			return "20.20";
		});
		serverThread = server.runOnThread();
	}

	private static void setUpThermoServer() throws IOException {
		process = Runtime.getRuntime().exec("java -jar ../ThermoServer.jar");
	}

	@BeforeClass
	public static void setUpBeforeClass() throws IOException {
		setUpMockServer();
//		setUpThermoServer();

		try {
			MainCtxAppl.initTheContext();

			crslogger = QActorTestUtils.waitForQActorToStart("crslogger");
			temperatureagent = QActorTestUtils.waitForQActorToStart("temperatureagent");
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@AfterClass
	public static void tearDownAfterClass() {
		if (process != null && process.isAlive())
			process.destroyForcibly();

		if (serverThread != null && serverThread.isAlive())
			serverThread.interrupt();
	}

	@Test
	public void temperatureEmissionTest() {
		try {
			int period = Integer.parseInt(temperatureagent.solveGoal("requestPeriod(P)").getVarValue("P").toString());
			Thread.sleep(period + 1000);

			if (serverThread != null)
				Assert.assertFalse(receivedMsgs.isEmpty());

			Assert.assertTrue(QActorTestUtils.isEventReceived(crslogger, "temperature", "temperature(D)"));
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

}
