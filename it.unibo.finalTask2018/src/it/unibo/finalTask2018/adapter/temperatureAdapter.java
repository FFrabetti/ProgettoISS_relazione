package it.unibo.finalTask2018.adapter;

import it.unibo.qactors.akka.QActor;
import scala.util.Random;

public class temperatureAdapter {
	
	static int cont = 0;
	
	public static void getTemperature(QActor qa) {
		double t;
		try {
			t = it.unibo.thermo.appl.Main.getT();
		} catch(Exception e) {
			cont++;
			t = 20+(cont%10); // 20-29
		}
		
		qa.addRule("currentTemp(" + t + ")");

//		Random r=new Random();
//		qa.addRule("currentTemp(" + r.nextInt(36) +")");
	}

}
