package it.unibo.finalTask2018;

import java.io.BufferedReader;
import java.io.IOException;

import it.unibo.qactors.akka.QActor;
import it.unibo.sockutils.tcp.TCPClient;

public class tempClient {
	
	// se ricevo questo esatto valore, probabilmente ci sono stati problemi con il server
	private static final double DEFAULT_TEMP = 25.0195;
	
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
		// "host" dot-notation conversion: addr(X,Y,W,Z) -> X.Y.W.Z
		int index = host.indexOf('(');
		if(index>=0) {
			String inner = host.substring(index+1, host.length()-1); // no ( and )
			host = inner.replace(',','.');
		}
		
		System.out.println("Connecting to: " + host + ":" + port);
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
			if(temp==null)
				throw new IOException("br.readLine()==null");
		} catch(Exception e) {
//			e.printStackTrace();
//			System.out.println(tempClient.class.getSimpleName() + ": " + e.getMessage());
			temp = String.valueOf(DEFAULT_TEMP);
		}
		
		qa.addRule("currentTemp(" + temp + ")");
//		qa.emit("temperature", "temperature(" + t + ")");
	}
	
}
