package it.unibo.sockutils.udp;

import java.util.function.UnaryOperator;

public class UDPServerThread extends Thread {
	
	private static final UnaryOperator<String> DEF_CONSUMER = s -> s;

	private UDPServer server;
	private UnaryOperator<String> consumer;
	
	public UDPServerThread(UDPServer server, UnaryOperator<String> consumer) {
		this.server = server;
		this.consumer = consumer;
	}
	
	public UDPServerThread(UDPServer server) {
		this(server, DEF_CONSUMER);
	}
	
	@Override
	public void run() {
		try {
			while(true) {
				System.out.println("Server: in attesa di richieste...");
				
				String msg = server.readMessage();
				
				String answ = consumer.apply(msg);
				
				server.writeMessage(answ);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
