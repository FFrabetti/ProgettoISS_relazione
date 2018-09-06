package it.unibo.test.pa;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import it.unibo.ctxApplPA.MainCtxApplPA;
import it.unibo.ctxRealRobotMock.MainCtxRealRobotMock;
import it.unibo.finalTask2018.adapter.lampAdapter;
import it.unibo.qactor.testutils.QATesting;
import it.unibo.qactors.akka.QActor;
import it.unibo.sockutils.tcp.TCPClient;

public class ArchIntegrationTest extends QATesting {

	private static QActor controller;
	private static Process robot;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		MainCtxApplPA.initTheContext();
		
		controller = waitForQActorToStart("controllerpa");
		
		robot = execMain(MainCtxRealRobotMock.class);
		Thread.sleep(10000);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		stopProcess(robot);
		System.out.println("MainCtxRealRobotMock stopped");
	}
	
	@Test
	public void sensorsControllerActuatorsTest() throws Exception {
		TCPClient rClient = new TCPClient(8999);
		BufferedReader robotReader = rClient.getBufferedReader();
		Thread.sleep(5000);
		
		// usercmd -> robot: forward, led: blink
		controller.addRule("canMove");
		controller.emit("usercmd", "usercmd(robotgui(w(4)))");
		Thread.sleep(2000);

		assertEquals("blink", lampAdapter.getState());
		
		rClient.writeLine("getState");
		assertEquals("forward", robotReader.readLine());
		rClient.close();
	}

}
