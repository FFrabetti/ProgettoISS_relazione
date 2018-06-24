package it.unibo.finalTask2018.adapter;

import it.unibo.qactors.akka.QActor;
import scala.util.Random;

public class temperatureAdapter {
	
	static int cont = 0;
	
	public static void getTemperature(QActor qa) {
		//qa.addRule("currentTemp(" + cont++ + ")");
		Random r=new Random();
		qa.addRule("currentTemp(" + r.nextInt(36) +")");
	}

}
