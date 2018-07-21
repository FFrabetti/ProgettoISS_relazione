package it.unibo.finalTask2018.adapter;

import it.unibo.qactors.akka.QActor;

import it.unibo.raspclient.TempClient;

public class raspAdapter {

	private static final String[] EMPTY_ARGS = new String[0];
	
//	public static boolean isLedConn = false;
	public static boolean isTempConn = false;
	
	public static void init(QActor qa, String type) {
		try {
			switch(type) {
//			case "led":
//				if(!isLedConn)
//					LedClient.init(EMPTY_ARGS);
//				isLedConn = true;
//				break;
			case "temp":
				if(!isTempConn)
					TempClient.init(EMPTY_ARGS);
				isTempConn = true;
				break;
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void getTemperature(QActor qa) {
		try {
			double t = TempClient.getTemperature();
			qa.addRule("currentTemp(" + t + ")");
//			qa.emit("temperature", "temperature(" + t + ")");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
