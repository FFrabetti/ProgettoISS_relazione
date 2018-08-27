package it.unibo.raspRobot;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.UnknownHostException;

import it.unibo.qactors.akka.QActor;
import it.unibo.sockutils.tcp.TCPClient;

public class robotClient {
	
	private static TCPClient client;
	
	public static void init(QActor qa, String host, String port) throws NumberFormatException, UnknownHostException, IOException {
		client = new TCPClient(host, port);
		
		new Thread(() -> {
			try {
				BufferedReader br = client.getBufferedReader();
	
				while(true) {
					String line = br.readLine();
					System.out.println("sonar: " + line);
					
					qa.emit("frontSonar", "sonar(" + line + ")");
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}).start();
	}
	
	public synchronized static void sendCmd(QActor qa, String cmd) throws IOException {
		client.writeLine(cmd);
	}

}
