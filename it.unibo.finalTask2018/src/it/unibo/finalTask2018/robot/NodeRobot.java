package it.unibo.finalTask2018.robot;

import it.unibo.qactors.akka.QActor;
import it.unibo.utils.clientTcp;

public class NodeRobot implements DDRobot {

	private static final String PATH = "../nodeServer/ConfigurableThreejsApp";
	
	@Override
	public void setUpEnvironment(QActor qa, String host, int port) {
		System.out.println("setting up the environment...");

		/* startServerPath.bat
		 * ----------------------------
		 * if NOT "%1" == "" cd %1
		 * cd server
		 * cd src
		 * start cmd /k node main 8999
		 */
		try {
			Runtime.getRuntime().exec(PATH + "/startServerPath.bat " + PATH); // + " > test.txt 2>&1");
			// wait for the server to start...
			Thread.sleep(10000); // 10s
			clientTcp.initClientConn(qa, host, port);	
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	// https://github.com/PierfrancescoSoffritti/configurable-threejs-app#server-input
	
	@Override
	public void stop(QActor qa) {
		// This message will stop the player's movement. Rotations aren't stoppable.
		clientTcp.sendCmd(qa, "alarm");
	}
	
	@Override
	public void forward(QActor qa) {
		clientTcp.sendCmd(qa, "moveForward", -1); // "forever", until an obstacle is reached
//		clientTcp.sendCmd(qa, "moveForward");
	}

	@Override
	public void backward(QActor qa) {
		clientTcp.sendCmd(qa, "moveBackward", -1); // "forever", until an obstacle is reached
//		clientTcp.sendCmd(qa, "moveBackward");
	}

	@Override
	public void left(QActor qa) {
		clientTcp.sendCmd(qa, "turnLeft");
	}

	@Override
	public void right(QActor qa) {
		clientTcp.sendCmd(qa, "turnRight");
	}
	
}

//public class robotNode {
//
//public static void init(QActor qa) throws Exception {
//	clientTcp.initClientConn(qa);
//}
//
//public static void init(QActor qa, String hostName, String port) throws Exception {
//	clientTcp.initClientConn(qa, hostName, Integer.parseInt(port));
//}
//
//// https://github.com/PierfrancescoSoffritti/configurable-threejs-app#server-input
//
//public static void robotStop(QActor qa) {
//	// This message will stop the player's movement. Rotations aren't stoppable.
//	clientTcp.sendCmd(qa, "alarm");
//}
//
//public static void robotForward(QActor qa) {
//	clientTcp.sendCmd(qa, "moveForward", -1); // "forever", until an obstacle is reached
////	clientTcp.sendCmd(qa, "moveForward");
//}
//
//public static void robotBackward(QActor qa) {
//	clientTcp.sendCmd(qa, "moveBackward", -1); // "forever", until an obstacle is reached
////	clientTcp.sendCmd(qa, "moveBackward");
//}
//
//public static void robotLeft(QActor qa) {
//	clientTcp.sendCmd(qa, "turnLeft");
//}
//
//public static void robotRight(QActor qa) {
//	clientTcp.sendCmd(qa, "turnRight");
//}
//}
