package it.unibo.finalTask2018.robotDDR;

import it.unibo.qactors.akka.QActor;

public class robotMoves {
	public static void robotStop(QActor qa) {
		System.out.println("STOP");
	}
	
	public static void robotForward(QActor qa) {
		System.out.println("FORWARD");
	}

	public static void robotBackward(QActor qa) {
		System.out.println("BACKWARD");
	}

	public static void robotLeft(QActor qa) {
		System.out.println("LEFT");
	}

	public static void robotRight(QActor qa) {
		System.out.println("RIGHT");
	}
}
