package it.unibo.finalTask2018.ra;

import it.unibo.qactors.akka.QActor;

public class robotAdapter {
	
	private static String status = "stop";
	
	public static void setUpEnvironment(QActor qa) {
		new Thread(() -> {
			try {
				Thread.sleep(1000);
				qa.emit("sonarSensor", "sonar(sonar1,8)");
				System.out.println("Emitting sonarSensor : sonar(sonar1,8)");
				
				Thread.sleep(1000);
				qa.emit("frontSonar", "sonar(10)");
				System.out.println("Emitting frontSonar : sonar(10)");
			} catch (InterruptedException e) { }
		}).start();
	}

	public static void robotStop(QActor qa) {
		status = "stop";
		System.out.println("STOP");
	}
	
	public static void robotForward(QActor qa) {
		status = "forward";
		System.out.println("FORWARD");
	}

	public static void robotBackward(QActor qa) {
		status = "backward";
		System.out.println("BACKWARD");
	}

	public static void robotLeft(QActor qa) {
		status = "left";
		System.out.println("LEFT");
	}

	public static void robotRight(QActor qa) {
		status = "right";
		System.out.println("RIGHT");
	}

	// for testing
	public static String getStatus() {
		return status;
	}
	
}
