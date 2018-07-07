package it.unibo.sockutils.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class UDPServer extends UDPClient {

	public UDPServer(InetAddress addr, int port) throws UnknownHostException, IOException {
		super();
		
		setSocket(new DatagramSocket(port, addr));
		setPacket(new DatagramPacket(getBuffer(), getBuffer().length));
		
		System.out.println("Server avviato: " // + InetAddress.getLocalHost()
			+ getSocket().getLocalAddress().getHostAddress() + ":" + getSocket().getLocalPort()
		);
	}

	public UDPServer(int port) throws NumberFormatException, UnknownHostException, IOException {
		this(null, port);
	}
	
	public UDPServer(String addr, String port) throws NumberFormatException, UnknownHostException, IOException {
		this(InetAddress.getByName(addr), Integer.parseInt(port));
	}

	public UDPServer(String port) throws NumberFormatException, UnknownHostException, IOException {
		this(Integer.parseInt(port));
	}

}
