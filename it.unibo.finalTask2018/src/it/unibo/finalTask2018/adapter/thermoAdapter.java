package it.unibo.finalTask2018.adapter;

import it.unibo.qactors.akka.QActor;

public class thermoAdapter {

	public static void initGUI(QActor qa) {
		new Thread(() -> {
			try {
				while (true) {
					Thread.sleep(10000);
					thermoAdapter.emitTemp(qa);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).start();

		it.unibo.thermo.appl.Main.launch();
	}

	public static void emitTemp(QActor qa) {
		double t = it.unibo.thermo.appl.Main.getT();

		qa.emit("temperature", "temperature(" + t + ")");
//		System.out.println("emitting: " + t);
	}

}