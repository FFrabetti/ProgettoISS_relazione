package it.unibo.finalTask2018.ra;

import java.time.LocalTime;

import it.unibo.qactors.akka.QActor;

public class clockAdapter {
	
	private static int[] currentTime = new int[]{0,0};
	
	public static void getTime(QActor qa) {
		LocalTime lt = LocalTime.now();
		currentTime[0] = lt.getHour();
		currentTime[1] = lt.getMinute();
		
		qa.addRule("currentTime(" + currentTime[0] + "," + currentTime[1] + ")");
	}

	// for testing
	public static int[] getCurrentTime() {
		return currentTime;
	}
	
}
