package it.unibo.custom.gui;

import it.unibo.qactors.akka.QActor;

public class BlinkingThread extends Thread{
	
	private QActor qa;
	
	public BlinkingThread(QActor qa) {
		this.qa=qa;
	}
	
	public void run() {
		int cont=0;
		for(;;) {
			try {
				customBlsGui.setLed(qa,"on");
				Thread.sleep(200);
				customBlsGui.setLed(qa,"off");
				Thread.sleep(200);

			} catch (InterruptedException e) {
				break;
			}
			//Per debug
			System.out.println("DEBUG:"+cont++ );
		}
	}

}
