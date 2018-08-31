package it.unibo.finalTask2018.ra;

import it.unibo.qactors.akka.QActor;

public class temperatureAdapter {
	
	private static double currentTemp = 25;

//	private static final Random rand = new Random();
	
	public static void getTemperature(QActor qa) {
//		double t = it.unibo.thermo.appl.Main.getT();
		
		// la temperatura corrente varia max di +/- 1 ad ogni richiesta
//		double t = currentTemp + rand.nextDouble() * (rand.nextBoolean() ? 1 : -1);
//		currentTemp = t;
		
		qa.addRule("currentTemp(" + currentTemp + ")");
	}

	// for testing
	public static double getCurrentTemp() {
		return currentTemp;
	}
	
}
