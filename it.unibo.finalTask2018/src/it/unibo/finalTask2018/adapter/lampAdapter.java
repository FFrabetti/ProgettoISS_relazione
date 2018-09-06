package it.unibo.finalTask2018.adapter;

import it.unibo.qactors.akka.QActor;

public class lampAdapter {

	private static String state = "off";
	
	public static void init(QActor qa) {
		it.unibo.custom.gui.customBlsGui.createCustomLedGui(qa);
	}

	public static void setLamp(QActor qa, String cmd) {
		it.unibo.custom.gui.customBlsGui.setLed(qa, cmd);
		state = cmd;
	}
	
	// for testing
	public static String getState() {
		return state;
	}
	
}
