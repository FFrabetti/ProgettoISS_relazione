import static org.junit.Assert.assertTrue;

import java.util.LinkedList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import it.unibo.ctxAppl.MainCtxAppl;
import it.unibo.ctxRealRobotRasp.MainCtxRealRobotRasp;
import it.unibo.qactor.testutils.QATesting;
import it.unibo.qactors.akka.QActor;
import it.unibo.sockutils.tcp.TCPServer;

public class RealRobotTest extends QATesting {

	private static QActor realrobotrasp;
	private static QActor ledagent;
	private static QActor rrrlogger;
	
	private static Process appl;
	private static final int SRV_PORT = 6666;
	private static List<String> receivedMsgs = new LinkedList<>();
	private static Thread serverThread;
	private static final String FRONT = "10.10";
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// in alternativa a TCPServer, avviare (python3):
		// ..\raspberry\pyServer\server_mock_no_arduino.py
		
		TCPServer server = new TCPServer(SRV_PORT, msg -> {
			receivedMsgs.add(msg);
			return FRONT;		// front sonar
		});
		serverThread = server.runOnThread();
		
		System.out.println("Starting MainCtxAppl...");
		appl = execMain(MainCtxAppl.class);
		Thread.sleep(5000);
		
		MainCtxRealRobotRasp.initTheContext();

		realrobotrasp = waitForQActorToStart("realrobotrasp");
		ledagent = waitForQActorToStart("ledagent");
		rrrlogger = waitForQActorToStart("rrrlogger");
		Thread.sleep(2000);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		stopProcess(appl);
		
		if(serverThread != null && serverThread.isAlive())
			serverThread.interrupt();
	}
	
	@Test
	public void moveRobotReceptionTest() throws Exception {
		ledagent.emit("ctrlEvent", "ctrlEvent(robot,r1,w(0))");
		Thread.sleep(1000);
		ledagent.emit("ctrlEvent", "ctrlEvent(robot,r1,a(0))");
		Thread.sleep(1000);
		
		String wCmd = realrobotrasp.solveGoal("map(w(0),OUT)").getTerm("OUT").toString();
		String aCmd = realrobotrasp.solveGoal("map(a(0),OUT)").getTerm("OUT").toString();
		assertTrue(receivedMsgs.contains(wCmd));
		assertTrue(receivedMsgs.contains(aCmd));
	}

	@Test
	public void lightCmdReceptionTest() throws Exception {
		realrobotrasp.emit("lightCmd", "lightCmd(on)");
		Thread.sleep(1000);
		
		String onCmd = ledagent.solveGoal("map(on,OUT)").getTerm("OUT").toString();
		assertTrue(receivedMsgs.contains(onCmd));
	}

	@Test
	public void frontSonarEmissionTest() throws Exception {
		// il server mock (TCPServer) risponde ad ogni comando ricevuto inviando un dato del sonar
		realrobotrasp.emit("lightCmd", "lightCmd(off)");
		Thread.sleep(1000);
		assertTrue(isEventReceived(rrrlogger, "frontSonar", "sonar(" + FRONT + ")"));
	}

}
