package it.unibo.sockutils.tcp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.function.UnaryOperator;

public class TCPServerThread extends Thread {

	private Socket socket;
	private UnaryOperator<String> consumer;

	public TCPServerThread(Socket socket, UnaryOperator<String> consumer) {
		this.socket = socket;
		this.consumer = consumer;
	}

	@Override
	public void run() {
		System.out.println(getName() + ": avviato");

		try {
			dorun();
		} catch (Exception e) {
			/*
			 * try-catch per socket.close(): essendo run() un metodo ridefinito non permette
			 * di propagare l'eccezione
			 */
		} finally {
			System.out.println(getName() + ": chiusura connessione.");
		}
	}

	private void dorun() throws IOException {
		try {
			DataInputStream inSock = new DataInputStream(socket.getInputStream());
			DataOutputStream outSock = new DataOutputStream(socket.getOutputStream());

			// Lettura da input stream fino a chiusura della connessione
			// o shutdownInput() -> EOFException
			while (true) {
				String richiesta = inSock.readUTF();
				System.out.println(getName() + " - letto: " + richiesta);

				String risposta = consumer.apply(richiesta);

				outSock.writeUTF(risposta);
				System.out.println(getName() + " - inviato: " + risposta);
			}

		} catch (Exception e) {
			System.out.println(getName() + ": " + e.getMessage());
		} finally {
			socket.close();
		}
	}
}
