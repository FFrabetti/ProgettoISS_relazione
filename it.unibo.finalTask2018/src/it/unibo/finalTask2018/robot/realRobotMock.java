package it.unibo.finalTask2018.robot;

import it.unibo.finalTask2018.robot.ledMock.ILed;
import it.unibo.finalTask2018.robot.ledMock.ledMockGui;
import it.unibo.custom.gui.customBlsGui;
import it.unibo.qactors.akka.QActor;

public class realRobotMock {

	private static ILed led;

	public static void setUpGui(QActor qa) {
		led = new ledMockGui();
		System.out.println("starting ledMock");
	}

	public static void robotStop(QActor qa) {
		System.out.println("STOP");
		if (led.isOn())
			led.turnOff();
	}

	public static void robotForward(QActor qa) {
		System.out.println("FORWARD");
		if (!led.isOn())
			led.turnOn();
	}

	public static void robotBackward(QActor qa) {
		System.out.println("BACKWARD");
		if (!led.isOn())
			led.turnOn();
	}

	public static void robotLeft(QActor qa) {
		System.out.println("LEFT");
		if (led.isOn())
			led.turnOff();
	}

	public static void robotRight(QActor qa) {
		System.out.println("RIGHT");
		if (led.isOn())
			led.turnOff();
	}
}
