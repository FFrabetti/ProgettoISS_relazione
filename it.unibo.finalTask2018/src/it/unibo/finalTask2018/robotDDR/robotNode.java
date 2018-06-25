package it.unibo.finalTask2018.robotDDR;

import it.unibo.qactors.akka.QActor;
import it.unibo.utils.clientTcp;

public class robotNode {
	
	public static void init(QActor qa) throws Exception {
		clientTcp.initClientConn(qa);
	}

	public static void init(QActor qa, String hostName, String port) throws Exception {
		clientTcp.initClientConn(qa, hostName, Integer.parseInt(port));
	}
	
	// https://github.com/PierfrancescoSoffritti/configurable-threejs-app#server-input
	
	public static void robotStop(QActor qa) {
		// This message will stop the player's movement. Rotations aren't stoppable.
		clientTcp.sendCmd(qa, "alarm");
	}
	
	public static void robotForward(QActor qa) {
		clientTcp.sendCmd(qa, "moveForward", -1); // "forever", until an obstacle is reached
//		clientTcp.sendCmd(qa, "moveForward");
	}

	public static void robotBackward(QActor qa) {
		clientTcp.sendCmd(qa, "moveBackward", -1); // "forever", until an obstacle is reached
//		clientTcp.sendCmd(qa, "moveBackward");
	}

	public static void robotLeft(QActor qa) {
		clientTcp.sendCmd(qa, "turnLeft");
	}

	public static void robotRight(QActor qa) {
		clientTcp.sendCmd(qa, "turnRight");
	}
}
