package it.unibo.finalTask2018.adapter;

import it.unibo.qactors.akka.QActor;

public class envAdapter {

	public static void setUpEnvironment(QActor qa) {
		// TODO Auto-generated method stub
		System.out.println("TODO: setting up the virtual environment...");
		
		try {
			
			Thread.sleep(2);
			qa.emit("sonarSensor", "sonar(envAdapter, 8)");
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
