package it.unibo.finalTask2018.robot;

import it.unibo.finalTask2018.robot.ledMock.ILed;
import it.unibo.finalTask2018.robot.ledMock.LedMockGui;
import it.unibo.qactors.akka.QActor;

public class RealRobotMock implements DDRobot {

	private ILed led;
	private Thread blinkingT;

	@Override
	public void setUpEnvironment(QActor qa, String host, int port) {
		led = new LedMockGui();
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
		blinkLed(false);
	}

	@Override
	public void forward(QActor qa) {
		System.out.println("FORWARD");
		blinkLed(true);
	}

	@Override
	public void backward(QActor qa) {
		System.out.println("BACKWARD");
		blinkLed(true);
	}

	@Override
	public void left(QActor qa) {
		System.out.println("LEFT");
		blinkLed(false);
	}

	@Override
	public void right(QActor qa) {
		System.out.println("RIGHT");
		blinkLed(false);
	}
	
	private void blinkLed(boolean on) {
		if(on && (blinkingT==null || !blinkingT.isAlive())) {
			blinkingT = new Thread(() -> {
				try {
					while(true) {
						Thread.sleep(200);
						if(led.isOn())
							led.turnOff();
						else
							led.turnOn();
					}
				} catch(InterruptedException e) {
//					e.printStackTrace();
				}
			});
			blinkingT.start();
		}
		else if(!on && blinkingT!=null && blinkingT.isAlive()){
			blinkingT.interrupt();
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// waiting for the thread to stop
			}
			led.turnOff();
		}
	}
}
