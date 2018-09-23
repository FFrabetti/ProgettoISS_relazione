package it.unibo.finalTask2018.robot;

import it.unibo.qactors.akka.QActor;
import it.unibo.sockutils.tcp.TCPServer;

public class MockRobot implements DDRobot {
	
	private String state = "stop";
	
	@Override
	public void setUpEnvironment(QActor qa, String host, int port) {
		System.out.println("setting up the environment...");
		
		// for testing
		TCPServer server = new TCPServer(port, s -> getState());
		server.runOnThread();
		
		// testing the emission of a sonarSensor event
		new Thread(() -> {
			try {
				Thread.sleep(1000);
				qa.emit("sonarSensor", "sonar(envAdapter, 8)");
				System.out.println("emitting event sonarSensor : sonar(envAdapter, 8)");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).start();
	}

	private String getState() {
		return state;
	}
	
	private void setState(String strState) {
		state = strState;
		System.out.println(state);
	}
	
	@Override
	public void stop(QActor qa) {
		setState("stop");
	}
	
	@Override
	public void forward(QActor qa) {
		setState("forward");
	}

	@Override
	public void backward(QActor qa) {
		setState("backward");
	}

	@Override
	public void left(QActor qa) {
		setState("left");
	}

	@Override
	public void right(QActor qa) {
		setState("right");
	}
	
}
