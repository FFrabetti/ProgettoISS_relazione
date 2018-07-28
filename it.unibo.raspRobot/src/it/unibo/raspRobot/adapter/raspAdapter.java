package it.unibo.raspRobot.adapter;

import java.io.IOException;
import java.net.UnknownHostException;

import it.unibo.ledrasp.LedRasp;
import it.unibo.qactors.akka.QActor;

public class raspAdapter {
	
	public static void setUpEnvironment(QActor qa) throws NumberFormatException, UnknownHostException, IOException {
		LedRasp.init();
	}
	
	public static void robotStop(QActor qa) {
		System.out.println("stop");
	}

	public static void robotForward(QActor qa) {
		System.out.println("forward");
	}

	public static void robotBackward(QActor qa) {
		System.out.println("backward");
	}

	public static void robotLeft(QActor qa) {
		System.out.println("left");
		LedRasp.sendLedCmd("left");
	}

	public static void robotRight(QActor qa) {
		System.out.println("right");
		LedRasp.sendLedCmd("right");
	}

}
