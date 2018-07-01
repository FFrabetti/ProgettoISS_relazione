package it.unibo.finalTask2018.adapter;

import it.unibo.finalTask2018.robot.DDRobot;
import it.unibo.finalTask2018.robot.mockRobot;
import it.unibo.finalTask2018.robot.NodeRobot;
import it.unibo.qactors.akka.QActor;

public class robotAdapter {

	private static final String HOST = "localhost";
	private static final int PORT = 8999;
	
	private static DDRobot robot = new mockRobot();
	
	public static void useImpl(QActor qa, String name) {
		if("node".equals(name))
			robot = new NodeRobot();
	}
	
	// ---------------------------------------------------------
	
	public static void setUpEnvironment(QActor qa) {
		robot.setUpEnvironment(qa, HOST, PORT);
	}

	public static void setUpEnvironment(QActor qa, String host, String port) {
		robot.setUpEnvironment(qa, host, Integer.parseInt(port));
	}
	
	public static void robotStop(QActor qa) {
		robot.stop(qa);
	}
	
	public static void robotForward(QActor qa) {
		robot.forward(qa);
	}

	public static void robotBackward(QActor qa) {
		robot.backward(qa);
	}

	public static void robotLeft(QActor qa) {
		robot.left(qa);
	}

	public static void robotRight(QActor qa) {
		robot.right(qa);
	}
	
}
