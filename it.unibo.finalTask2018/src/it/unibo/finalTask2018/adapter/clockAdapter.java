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
	
	
	public static void initGUI(QActor qa) {
		new Thread(() -> {
			try {
				Thread.sleep(5000);
				while (true) {
					Thread.sleep(10000);
					clockAdapter.emitTime(qa);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).start();

		it.unibo.clock.appl.Main.launch();
	}

	public static void emitTime(QActor qa) {
		int[] time = it.unibo.clock.appl.Main.getTime();

		qa.emit("clock", "clock(" + time[0] + "," + time[1] + ")");
	}
	
}
