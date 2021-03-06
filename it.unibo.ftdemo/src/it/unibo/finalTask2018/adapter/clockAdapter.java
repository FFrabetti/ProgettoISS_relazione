package it.unibo.finalTask2018.adapter;

import java.time.LocalTime;

import it.unibo.qactors.akka.QActor;

public class clockAdapter {
		
	public static void initGUI(QActor qa) {
		LocalTime lt = LocalTime.now();
		it.unibo.clock.appl.Main.launch(lt.getHour(), lt.getMinute());
	}

	public static void getTime(QActor qa) {
		int[] time = it.unibo.clock.appl.Main.getTime();
		qa.addRule("currentTime(" + time[0] + "," + time[1] + ")");
	}
	
	public static void emitTime(QActor qa) {
		int[] time = it.unibo.clock.appl.Main.getTime();
		qa.emit("clock", "clock(" + time[0] + "," + time[1] + ")");
	}
	
}
