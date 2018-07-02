package it.unibo.finalTask2018.robot.ledMock;

import java.awt.Color;

import it.unibo.custom.gui.customBlsGui;

public class ledMockGui extends customBlsGui implements ILed {

	private static boolean on = false;
	
	public ledMockGui() {
		super();
		createCustomLedGui(null, Color.CYAN);
	}
	
	@Override
	public void turnOn() {
		blinkLed(null, "on");
		on = true;
	}

	@Override
	public void turnOff() {
		blinkLed(null, "off");
		on = false;
	}

	@Override
	public boolean isOn() {
		return on;
	}

}
