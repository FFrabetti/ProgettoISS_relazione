package it.unibo.finalTask2018.adapter;

import it.unibo.qactors.akka.QActor;

public class envAdapter {

	public static void setUpEnvironment(QActor qa) {
		System.out.println("setting up the environment...");
		
		// testing the emission of a sonarSensor event
		try {
			Thread.sleep(2000);
			qa.emit("sonarSensor", "sonar(envAdapter, 8)");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
