import static org.junit.Assert.fail;

import java.util.LinkedList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import it.unibo.ctxRealRobotRasp.MainCtxRealRobotRasp;
import it.unibo.qactor.testutils.QActorTestUtils;
import it.unibo.qactors.akka.QActor;
import it.unibo.sockutils.tcp.TCPServer;

public class RealRobotTest {

	private static QActor realrobotrasp;
	private static QActor ledagent;
	private static QActor realrobottest;

	private static final int SRV_PORT = 6666;
	private static List<String> receivedMsgs = new LinkedList<>();
	private static Thread serverThread;
	
	@BeforeClass
	public static void setUpBeforeClass() {
		// in alternativa a TCPServer server, avviare (python3):
		// raspberry\pyServer\server_mock_no_arduino.py
		
		TCPServer server = new TCPServer(SRV_PORT, msg -> {
			receivedMsgs.add(msg);
			return "10.10";
		});
		serverThread = server.runOnThread();
		
		try {
			MainCtxRealRobotRasp.initTheContext();

			realrobotrasp = QActorTestUtils.waitForQActorToStart("realrobotrasp");
			ledagent = QActorTestUtils.waitForQActorToStart("ledagent");
			realrobottest = QActorTestUtils.waitForQActorToStart("realrobottest");
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@AfterClass
	public static void tearDownAfterClass() {
		if(serverThread!=null && serverThread.isAlive())
			serverThread.interrupt();
	}
	
	@Test
	public void movCmdReceptionTest() {
		try {
			realrobottest.emit("ctrlEvent", "ctrlEvent(robot,r1,w(0))");
			Thread.sleep(1000);
			Assert.assertTrue(QActorTestUtils.isMsgReceived(realrobotrasp, "moveRobot", "moveRobot(w(0))"));
			
			realrobottest.emit("ctrlEvent", "ctrlEvent(robot,r1,a(0))");
			Thread.sleep(1000);
			Assert.assertTrue(QActorTestUtils.isMsgReceived(realrobotrasp, "moveRobot", "moveRobot(a(0))"));
			
			// controllo cosa e' stato ricevuto dal server
			Assert.assertTrue(receivedMsgs.contains(realrobotrasp.solveGoal("map(w(0),OUT)").getVarValue("OUT").toString()));
			Assert.assertTrue(receivedMsgs.contains(realrobotrasp.solveGoal("map(a(0),OUT)").getVarValue("OUT").toString()));
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public void lightCmdReceptionTest() {
		try {
			realrobottest.emit("lightCmd", "lightCmd(on)");
			Thread.sleep(1000);
			Assert.assertTrue(QActorTestUtils.isEventReceived(ledagent, "lightCmd", "lightCmd(on)"));
			
			// controllo cosa e' stato ricevuto dal server
			Assert.assertTrue(receivedMsgs.contains(ledagent.solveGoal("map(on,OUT)").getVarValue("OUT").toString()));
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public void frontSonarEmissionTest() {
		try {
			// il server mock (TCPServer) risponde ad ogni comando ricevuto inviando un dato del sonar
			realrobottest.emit("lightCmd", "lightCmd(off)");
			Thread.sleep(1000);
			Assert.assertTrue(QActorTestUtils.isEventReceived(realrobottest, "frontSonar", "sonar(D)"));
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

}
