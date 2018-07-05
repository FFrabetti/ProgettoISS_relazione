package it.unibo.finalTask2018.adapter;

import it.unibo.qactors.akka.QActor;

public class thermoAdapter {

	public static void initGUI(QActor qa) {
		it.unibo.thermo.appl.Main.launch(20);
	}

	public static void emitTemp(QActor qa) {
		double t = it.unibo.thermo.appl.Main.getT();
		qa.emit("temperature", "temperature(" + t + ")");
	}

}
