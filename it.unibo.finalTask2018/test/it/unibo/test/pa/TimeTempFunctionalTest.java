package it.unibo.test.pa;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import it.unibo.ctxApplPA.MainCtxApplPA;
import it.unibo.ctxRealRobotMock.MainCtxRealRobotMock;
import it.unibo.finalTask2018.adapter.lampAdapter;
import it.unibo.qactor.testutils.QATesting;
import it.unibo.qactors.akka.QActor;
import it.unibo.sockutils.tcp.TCPClient;

public class TimeTempFunctionalTest extends QATesting {

	private static QActor controller;
	private static Process robot;
	
	private static TCPClient rClient;
	private static BufferedReader robotReader;
		
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		MainCtxApplPA.initTheContext();
		controller = waitForQActorToStart("controllerpa");
		
		robot = execMain(MainCtxRealRobotMock.class);
		Thread.sleep(10000);
		rClient = new TCPClient(8999);
		robotReader = rClient.getBufferedReader();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		rClient.close();
		stopProcess(robot);
		System.out.println("MainCtxRealRobotMock stopped");
	}
	
	@Before
	public void beforeTest() {
		// time e temperature ok
		setTempLimit(100);
		setTimeInterval(0, 100);
	}
	
	@Test
	public void temperatureOkKoTest() throws Exception {
		controller.solveGoal("changeModelItem(robot,r1,h(0))");
		
		// TempOk: t<limit, can move
		controller.emit("usercmd", "usercmd(robotgui(w(4)))");
		Thread.sleep(2000);
		assertEquals("blink", lampAdapter.getState());
		assertEquals("forward", getRobotState());

		// TempKo: stop
		setTempLimit(0);
		controller.solveGoal("getModelItem(t1,T), T2 is T+1,"
				+ "changeModelItem(temperature,t1,T2)");
		Thread.sleep(2000);
		assertEquals("off", lampAdapter.getState());
		assertEquals("stop", getRobotState());
		
		// TempOk: t>=limit, cannot move
		controller.emit("usercmd", "usercmd(robotgui(w(4)))");
		Thread.sleep(2000);
		assertEquals("off", lampAdapter.getState());
		assertEquals("stop", getRobotState());
	}

	@Test
	public void timeOkKoTest() throws Exception {
		controller.solveGoal("changeModelItem(robot,r1,h(0))");
		
		// TimeOk: lbound<t<hbound, can move
		controller.emit("usercmd", "usercmd(robotgui(w(4)))");
		Thread.sleep(2000);
		assertEquals("blink", lampAdapter.getState());
		assertEquals("forward", getRobotState());

		// TimeKo: stop
		setTimeInterval(0, 0);
		controller.solveGoal("getModelItem(c1,h(H,_)), H2 is H+1,"
				+ "changeModelItem(clock,c1,h(H2,0))");
		Thread.sleep(2000);
		assertEquals("off", lampAdapter.getState());
		assertEquals("stop", getRobotState());
		
		// TimeOk: t>=hbound, cannot move
		controller.emit("usercmd", "usercmd(robotgui(w(4)))");
		Thread.sleep(2000);
		assertEquals("off", lampAdapter.getState());
		assertEquals("stop", getRobotState());
	}
	
	// utilities
	private static String getRobotState() throws IOException {
		rClient.writeLine("getState");
		return robotReader.readLine();
	}

	private static void setTempLimit(int t) {
		controller.replaceRule(
				"limitTemperatureValue(_)",
				"limitTemperatureValue(" + t + ")"
		);
	}
	
	private static void setTimeInterval(int h1, int h2) {
		controller.replaceRule(
				"timeInterval(_,_)",
				"timeInterval(h(" + h1 + ",0),h(" + h2 + ",0))"
		);
	}
	
}
