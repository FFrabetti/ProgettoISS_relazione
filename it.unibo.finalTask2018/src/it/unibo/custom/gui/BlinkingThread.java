package it.unibo.custom.gui;

public class BlinkingThread extends Thread{
	
	private customBlsGui led;
	
	public BlinkingThread(customBlsGui led) {
		this.led=led;
	}
	
	public void run() {
		try {
			while(true) {
				Thread.sleep(200);
				led.setLedGui(true);
				Thread.sleep(200);
				led.setLedGui(false);
			} 
		} catch (InterruptedException e) { }
	}

}
