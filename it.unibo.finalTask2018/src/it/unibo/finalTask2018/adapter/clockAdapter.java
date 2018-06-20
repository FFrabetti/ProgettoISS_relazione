package it.unibo.finalTask2018.adapter;

import it.unibo.qactors.akka.QActor;

public class clockAdapter {
	
	static int cont = 0;
	
	public static void getTime(QActor qa) {
		qa.addRule("currentTime(" + cont++ + "," + cont*10 + ")");
	}

}
