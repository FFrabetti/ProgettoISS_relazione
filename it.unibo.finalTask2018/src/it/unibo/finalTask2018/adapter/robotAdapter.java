package it.unibo.finalTask2018.adapter;

import java.util.HashMap;
import java.util.Map;

import it.unibo.finalTask2018.robot.DDRobot;
import it.unibo.finalTask2018.robot.MockRobot;
import it.unibo.finalTask2018.robot.NodeRobot;
import it.unibo.finalTask2018.robot.RealRobotMock;
import it.unibo.qactors.akka.QActor;

public class robotAdapter {

	private static final String HOST = "localhost";
	private static final int PORT = 8999;
	private static final DDRobot DEFAULT = new MockRobot();
	
	private static Map<String,DDRobot> map = new HashMap<>();
	
	private static DDRobot getInstance(QActor qa) {
		return map.getOrDefault(qa.getName(), DEFAULT);
	}
	
	public static void useImpl(QActor qa, String name) {
		if("node".equals(name))
			map.put(qa.getName(), new NodeRobot());
		else if("realmock".equals(name))
			map.put(qa.getName(), new RealRobotMock());
	}
	
	// ---------------------------------------------------------
	
	public static void setUpEnvironment(QActor qa) {
		getInstance(qa).setUpEnvironment(qa, HOST, PORT);
	}

	public static void setUpEnvironment(QActor qa, String host, String port) {
		getInstance(qa).setUpEnvironment(qa, host, Integer.parseInt(port));
	}
	
	public static void robotStop(QActor qa) {
		getInstance(qa).stop(qa);
	}
	
	public static void robotForward(QActor qa) {
		getInstance(qa).forward(qa);
	}

	public static void robotBackward(QActor qa) {
		getInstance(qa).backward(qa);
	}

	public static void robotLeft(QActor qa) {
		getInstance(qa).left(qa);
	}

	public static void robotRight(QActor qa) {
		getInstance(qa).right(qa);
	}
	
}
