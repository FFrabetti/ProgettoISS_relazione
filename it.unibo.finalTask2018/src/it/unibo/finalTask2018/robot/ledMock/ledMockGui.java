package it.unibo.finalTask2018.robot.ledMock;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import it.unibo.custom.gui.BlinkingThread;
import it.unibo.custom.gui.customBlsGui;
import it.unibo.qactors.akka.QActor;

public class ledMockGui extends customBlsGui implements ILed {

	private static boolean on = false;
	
	public ledMockGui() {
		super();
		createCustomLedGui(null);
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
