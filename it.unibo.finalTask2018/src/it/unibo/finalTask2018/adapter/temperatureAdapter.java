package it.unibo.finalTask2018.adapter;

import it.unibo.qactors.akka.QActor;
import scala.util.Random;

public class temperatureAdapter {
	
	static int cont = 100;
	
	public static void getTemperature(QActor qa) {
		cont++;
		qa.addRule("currentTemp(" + (22+(cont/10)%5) + ")");
//		Random r=new Random();
//		qa.addRule("currentTemp(" + r.nextInt(36) +")");
	}

}
