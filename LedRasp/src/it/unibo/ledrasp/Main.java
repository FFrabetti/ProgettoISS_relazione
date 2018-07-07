package it.unibo.ledrasp;

import java.io.IOException;
import java.net.UnknownHostException;

import it.unibo.sockutils.tcp.TCPClient;
import it.unibo.sockutils.tcp.TCPServer;
import it.unibo.sockutils.udp.UDPClient;
import it.unibo.sockutils.udp.UDPServer;
import it.unibo.sockutils.udp.UDPServerThread;

public class Main {

	public static void main(String[] args) {
		
//		new Thread(() -> {
//			TCPServer server = new TCPServer(3333, s -> s);
//			server.startServer();
//		}).start();
		
		TCPServer server = new TCPServer(3333, s -> s);
		server.runOnThread();
		
		try(TCPClient client = new TCPClient(3333)) {
			
			client.writeMessage("ciao");
			
			String answer = client.readMessage();
			System.out.println("Ho ricevuto: " + answer);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		try {
			UDPServer udpServer = new UDPServer(4444);
			UDPServerThread ust = new UDPServerThread(udpServer, String::toUpperCase);	
			ust.start();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try(UDPClient udpClient = new UDPClient("localhost", "4444")) {
			
			udpClient.writeMessage("heila!");
		
			System.out.println("Ho ricevuto in risposta: " + udpClient.readMessage());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
