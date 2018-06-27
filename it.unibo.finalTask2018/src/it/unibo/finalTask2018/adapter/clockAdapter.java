package it.unibo.finalTask2018.adapter;

import it.unibo.qactors.akka.QActor;
import scala.util.Random;

public class clockAdapter {
	
	static int cont = 0;
	
	public static void getTime(QActor qa) {
		cont++;
		qa.addRule("currentTime(" + (6+(cont/6)%7) + "," + cont + ")");
//		Random r=new Random();
//		qa.addRule("currentTime(" + r.nextInt(24) + "," + r.nextInt(60) + ")");
	}
}
