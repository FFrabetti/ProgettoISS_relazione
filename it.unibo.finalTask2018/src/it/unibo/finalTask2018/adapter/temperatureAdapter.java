package it.unibo.finalTask2018.adapter;

import it.unibo.qactors.akka.QActor;

public class temperatureAdapter {
	
	static int cont = 0;
	public static void getTemperature(QActor qa) {
		qa.addRule("currentTemp(" + cont++ + ")");
	}

}
