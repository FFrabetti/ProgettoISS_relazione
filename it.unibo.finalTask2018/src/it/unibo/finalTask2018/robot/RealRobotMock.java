package it.unibo.finalTask2018.robot;

import it.unibo.finalTask2018.robot.ledMock.ILed;
import it.unibo.finalTask2018.robot.ledMock.ledMockGui;
import it.unibo.qactors.akka.QActor;

public class RealRobotMock implements DDRobot {

	private static ILed led;

	@Override
	public void setUpEnvironment(QActor qa, String host, int port) {
		led = new ledMockGui();
		led.turnOn();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		led.turnOff();
		System.out.println("starting ledMock");
	}

	@Override
	public void stop(QActor qa) {
		System.out.println("STOP");
		if (led.isOn())
			led.turnOff();
	}

	@Override
	public void forward(QActor qa) {
		System.out.println("FORWARD");
		if (!led.isOn())
			led.turnOn();
	}

	@Override
	public void backward(QActor qa) {
		System.out.println("BACKWARD");
		if (!led.isOn())
			led.turnOn();
	}

	@Override
	public void left(QActor qa) {
		System.out.println("LEFT");
		if (led.isOn())
			led.turnOff();
	}

	@Override
	public void right(QActor qa) {
		System.out.println("RIGHT");
		if (led.isOn())
			led.turnOff();
	}
}
