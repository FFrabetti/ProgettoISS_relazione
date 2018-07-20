package it.unibo.sockutils.tcp;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.UnaryOperator;

public class TCPServer {

	private static final UnaryOperator<String> DEF_CONSUMER = s -> s;
	
	private int port;
	private UnaryOperator<String> consumer;
	
	public TCPServer(int port, UnaryOperator<String> consumer) {
		if (port < 1024 || port > 65535)
			throw new IllegalArgumentException("Invalid port [1024-65535]");

		this.port = port;
		this.consumer = consumer;
	}

	public TCPServer(String port, UnaryOperator<String> consumer) {
		this(Integer.parseInt(port), consumer);
	}

	public TCPServer(int port) { // echo server
		this(port, DEF_CONSUMER);
	}	
	
	public void startServer() {
		try (ServerSocket serverSocket = new ServerSocket(port)) {
			serverSocket.setReuseAddress(true);
			System.out.println("Server: creata server socket " + serverSocket);

			// Ciclo di accettazione delle richieste
			while (true) {
				System.out.println("Server: in attesa di richieste...");
				try {
					// Bloccante fino ad una pervenuta connessione
					Socket clientSocket = serverSocket.accept();
					System.out.println("Server: connessione accettata: " + clientSocket);
					
					// SERVER PARALLELO
					// Servizio delegato ad un nuovo thread
					new TCPServerThread(clientSocket, consumer).start();
				} catch (Exception e) {
					System.out.println("Server: " + e.getMessage());
				}
			}
		} catch (Exception e) {
			System.out.println("Server: terminazione per errore ");
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public Thread runOnThread() {
		Thread t = new Thread(() -> startServer());
		t.start();
		return t;
	}
}
