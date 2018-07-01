package it.unibo.finalTask2018.robot;

import it.unibo.qactors.akka.QActor;

public class MockRobot implements DDRobot {

	@Override
	public void setUpEnvironment(QActor qa, String host, int port) {
		System.out.println("setting up the environment...");
		
		// testing the emission of a sonarSensor event
		try {
			Thread.sleep(20000);
			qa.emit("sonarSensor", "sonar(envAdapter, 8)");
			System.out.println("emitting event sonarSensor : sonar(envAdapter, 8)");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void stop(QActor qa) {
		System.out.println("STOP");
	}
	
	@Override
	public void forward(QActor qa) {
		System.out.println("FORWARD");
	}

	@Override
	public void backward(QActor qa) {
		System.out.println("BACKWARD");
	}

	@Override
	public void left(QActor qa) {
		System.out.println("LEFT");
	}

	@Override
	public void right(QActor qa) {
		System.out.println("RIGHT");
	}
	
}
