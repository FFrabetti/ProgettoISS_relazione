package it.unibo.finalTask2018.robot;

import it.unibo.qactors.akka.QActor;

public interface DDRobot {
	
	public void setUpEnvironment(QActor qa, String host, int port);
	
	public void stop(QActor qa);
	public void forward(QActor qa);
	public void backward(QActor qa);
	public void left(QActor qa);
	public void right(QActor qa);

}
