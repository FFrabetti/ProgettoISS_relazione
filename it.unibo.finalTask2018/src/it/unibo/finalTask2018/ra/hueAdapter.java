package it.unibo.finalTask2018.ra;

import it.unibo.qactors.akka.QActor;

public class hueAdapter {

	private static String currentState;
	
	public static void setLight(QActor qa, String cmd) {
		currentState = cmd;
		
		System.out.println("HueLamp: " + currentState);
	}
	
	// for testing
	public static String getCurrentState() {
		return currentState;
	}
	
}
