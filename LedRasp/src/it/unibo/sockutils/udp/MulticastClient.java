package it.unibo.sockutils.udp;

import java.io.*;
import java.net.*;

public class MulticastClient extends UDPClient {

	private InetAddress group;
	
	public MulticastClient(InetAddress group, int port) throws UnknownHostException, IOException {
		super();
		this.group = group;
		
		MulticastSocket socket = port>0 ? new MulticastSocket(port) : new MulticastSocket();
//		socket.setSoTimeout(20000); // 20 secondi

		socket.joinGroup(group);
    	System.out.println("Adesione al gruppo: " + group);

		setSocket(socket);
		setPacket(new DatagramPacket(getBuffer(), getBuffer().length));
	}
	
	public MulticastClient(String addr) throws UnknownHostException, IOException {
		this(InetAddress.getByName(addr), -1);
	}
	
	@Override
	public void close() throws Exception {
		((MulticastSocket)getSocket()).leaveGroup(group);
		
		super.close();
	}
}