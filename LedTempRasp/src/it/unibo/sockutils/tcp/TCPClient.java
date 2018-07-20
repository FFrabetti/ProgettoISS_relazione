package it.unibo.sockutils.tcp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCPClient implements AutoCloseable {

//		private InetAddress serverAddr;
//		private int serverPort;
		
		private Socket socket;
		private DataInputStream inSock;
		private DataOutputStream outSock;
		
		public TCPClient(InetAddress serverAddr, int serverPort) throws IOException {
			if(serverPort < 1024 || serverPort > 65535)
				throw new IllegalArgumentException("Invalid port [1024-65535]");
			
//			this.serverAddr = serverAddr;
//			this.serverPort = serverPort;
			
			socket = new Socket(serverAddr, serverPort);
			inSock = new DataInputStream(socket.getInputStream());
			outSock = new DataOutputStream(socket.getOutputStream());
		}
		
		public TCPClient(String addr, String port) throws NumberFormatException, UnknownHostException, IOException {
			this(InetAddress.getByName(addr), Integer.parseInt(port));
		}
		
		public TCPClient(String addr, int port) throws UnknownHostException, IOException {
			this(InetAddress.getByName(addr), port);
		}
		
		public TCPClient(int port) throws UnknownHostException, IOException {
			this("localhost", port);
		}
		
		public void writeMessage(String msg) throws IOException {
			outSock.writeUTF(msg);
//			System.out.println("Inviato: " + msg);
		}
		
		public String readMessage() throws IOException {
			String msg = inSock.readUTF();
//			System.out.println("Letto: " + msg);
			return msg;
		}

		public Socket getSocket() {
			return socket;
		}

		public DataInputStream getInputStream() {
			return inSock;
		}

		public DataOutputStream getOutputStream() {
			return outSock;
		}

		@Override
		public void close() throws Exception {
			socket.close();
		}
}
