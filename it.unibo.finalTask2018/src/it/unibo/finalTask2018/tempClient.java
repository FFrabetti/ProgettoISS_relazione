package it.unibo.finalTask2018;

import java.io.BufferedReader;

import it.unibo.qactors.akka.QActor;
import it.unibo.sockutils.tcp.TCPClient;

public class tempClient {
	
	private static final double DEFAULT_TEMP = 20.20;
	
	// SERVER ATTIVO: invio continuo di dati
//	private static double temperature = 0;	
	
//	public static void init(String host, String port) {
//		new Thread(() -> {
//			try(TCPClient client = new TCPClient(host, port)) {
//				BufferedReader br = client.getBufferedReader();
//
//				while(true) {
//					String line = br.readLine();
//					temperature = Double.parseDouble(line);
//					System.out.println(line);
//				}
//				
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}).start();
//	}

	// SERVER PASSIVO: invio di dati solo su richiesta da parte del client
	
	private static TCPClient client;
	private static BufferedReader br;
	
	public static void init(QActor qa, String host, String port) {
		try {
			client = new TCPClient(host, port);
			br = client.getBufferedReader();
		} catch(Exception e) {
//			e.printStackTrace();
			System.out.println(tempClient.class.getSimpleName() + ": " + e.getMessage());
		}
	}

	public static void temperatureRequest(QActor qa, String request) {
		String temp;
		try {
			client.writeLine(request);
			temp = br.readLine();
		} catch(Exception e) {
//			e.printStackTrace();
//			System.out.println(tempClient.class.getSimpleName() + ": " + e.getMessage());
			temp = String.valueOf(DEFAULT_TEMP);
		}
		
		qa.addRule("currentTemp(" + temp + ")");
//		qa.emit("temperature", "temperature(" + t + ")");
	}
	
}
