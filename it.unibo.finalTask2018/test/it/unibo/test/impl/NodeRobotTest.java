package it.unibo.test.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.BeforeClass;
import org.junit.Test;

import it.unibo.ctxNodeRobotTest.MainCtxNodeRobotTest;
import it.unibo.finalTask2018.robot.NodeRobot;
import it.unibo.qactor.testutils.QATesting;
import it.unibo.qactors.akka.QActor;
import it.unibo.sockutils.tcp.TCPServer;
import it.unibo.utils.clientTcp;

public class NodeRobotTest extends QATesting {

	private static QActor logger;
	private static List<String> receivedMsgs;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		MainCtxNodeRobotTest.initTheContext();

		logger = waitForQActorToStart("nrtlogger");
	}

	@Test
	public void tcpConnCmdEmissionTest() throws Exception {
		Thread t = null;
		try {
			receivedMsgs = new ArrayList<>();
			TCPServer server = new TCPServer(7777, s -> {
				receivedMsgs.add(s);
				return "";
			});
			t = server.runOnThread();

			clientTcp.initClientConn(logger, "localhost", 7777);
			// usa clientTcp per inviare i dati nel giusto formato (JSON-like)
			NodeRobot robot = new NodeRobot();
			robot.forward(logger);
			robot.backward(logger);
			robot.stop(logger);
			robot.left(logger);
			robot.right(logger);

			Thread.sleep(1000);

			assertEquals(5, receivedMsgs.size());
			assertEquals(";{\"arg\":-1,\"type\":\"moveForward\"};", receivedMsgs.get(0));
			assertEquals(";{\"arg\":-1,\"type\":\"moveBackward\"};", receivedMsgs.get(1));
			assertEquals(";{\"arg\":800,\"type\":\"alarm\"};", receivedMsgs.get(2));
			assertEquals(";{\"arg\":800,\"type\":\"turnLeft\"};", receivedMsgs.get(3));
			assertEquals(";{\"arg\":800,\"type\":\"turnRight\"};", receivedMsgs.get(4));
		} finally {
			if (t != null && t.isAlive())
				t.interrupt();
		}
	}

	@Test
	public void tcpConnEventsReceptionTest() throws Exception {
		Thread t = null;
		try {
			TCPServer server = new TCPServer(7778, s -> s);
			t = server.runOnThread();

			clientTcp.initClientConn(logger, "localhost", 7778);
			PrintWriter writer = clientTcp.getPrintWriter();

			writer.println(
					";{ \"type\": \"sonar-activated\", \"arg\": { \"sonarName\": \"sonar-1\", \"distance\": 1, \"axis\": \"x\" } };");
			writer.println(
					";{ \"type\": \"sonar-activated\", \"arg\": { \"sonarName\": \"sonar-2\", \"distance\": 2, \"axis\": \"x\" } };");
			writer.println(";{ \"type\": \"collision\", \"arg\": { \"objectName\": \"wall\" } };");
			writer.flush();

			Thread.sleep(1000);

			assertTrue(isEventReceived(logger, "sonarSensor", "sonar(sonar1,1)"));
			assertTrue(isEventReceived(logger, "sonarSensor", "sonar(sonar2,2)"));
			assertTrue(isEventReceived(logger, "frontSonar", "sonar(2)"));
		} finally {
			if (t != null && t.isAlive())
				t.interrupt();
		}
	}

//	@Test
	public void nodeServerTest() throws Exception {
		// start node server (.bat)
		// send move forward + wait
		// expect frontSonar event from a collision
		String path = "../nodeServer/ConfigurableThreejsApp";
		Process p = null;
		try {
			p = Runtime.getRuntime().exec(path + "/startServerTest.bat " + path);
			// wait for the server to start...
			Thread.sleep(10000);
			BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line;
			// waiting for:
			// TCP server listening on port 8999
			while ((line = br.readLine()) != null && !line.startsWith("TCP"))
				System.out.println(line);
			System.out.println("ok, letto: " + line);

			System.out.println("Trying to connect...");
			clientTcp.initClientConn(logger);
			NodeRobot robot = new NodeRobot();
			
			HttpClient client = HttpClientBuilder.create().build();
			HttpGet get = new HttpGet("http://localhost:8090/");
			HttpResponse response = client.execute(get);
			System.out.println(response.getStatusLine());
			
			Thread.sleep(1000);
			System.out.println("send backward");
			robot.backward(logger);
			
			Thread.sleep(20000);

			assertTrue(isEventReceived(logger, "frontSonar", "sonar(2)"));
		} finally {
			stopProcess(p);
		}
	}

}
