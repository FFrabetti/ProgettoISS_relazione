package it.unibo.finalTask2018.robot.ledMock;

import java.awt.Color;

import it.unibo.custom.gui.customBlsGui;

public class LedMockGui extends customBlsGui implements ILed {

	private static boolean on = false;
	
	public LedMockGui() {
		super();
		createCustomLedGui(null, Color.CYAN);
		turnOff();
	}
	
	@Override
	public void turnOn() {
		setLed(null, "on");
		on = true;
	}

	@Override
	public void turnOff() {
		setLed(null, "off");
		on = false;
	}

	@Override
	public boolean isOn() {
		return on;
	}

}
