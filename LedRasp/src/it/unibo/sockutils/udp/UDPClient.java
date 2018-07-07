package it.unibo.sockutils.udp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class UDPClient implements AutoCloseable {
	
	private static final int BUFF_SIZE = 256;
	
	private DatagramSocket socket;
	private	DatagramPacket packet;
	private byte[] buff = new byte[BUFF_SIZE];

	// per sottoclassi
	// ------------------------------------------------------
	protected UDPClient() {

	}
	
	protected void setSocket(DatagramSocket socket) {
		this.socket = socket;
	}
	
	protected void setPacket(DatagramPacket packet) {
		this.packet = packet;
	}
	
	protected byte[] getBuffer() {
		return buff;
	}
	// ------------------------------------------------------
	
	public UDPClient(InetAddress localAddr, InetAddress serverAddr, int serverPort) throws IOException {
		if(localAddr != null)
			socket = new DatagramSocket(0, localAddr);
		else
			socket = new DatagramSocket();
		
		packet = new DatagramPacket(buff, buff.length, serverAddr, serverPort);
		
		System.out.println("Client avviato: " // + InetAddress.getLocalHost()
				+ socket.getLocalAddress().getHostAddress() + ":" + socket.getLocalPort()
				+ " (server: " + serverAddr.getHostAddress() + ":" + serverPort + ")"
		);
	}
	
	public UDPClient(InetAddress serverAddr, int serverPort) throws IOException {
		this(null, serverAddr, serverPort);
	}
	
	public UDPClient(String local, String addr, String port) throws NumberFormatException, UnknownHostException, IOException {
		this(InetAddress.getByName(local), InetAddress.getByName(addr), Integer.parseInt(port));
	}
	
	public UDPClient(String addr, String port) throws UnknownHostException, IOException {
		this(null, addr, port);
	}
	
	public void writeMessage(String msg) throws IOException {
		ByteArrayOutputStream boStream = new ByteArrayOutputStream();
		DataOutputStream doStream = new DataOutputStream(boStream);
		
		doStream.writeUTF(msg);

		packet.setData(boStream.toByteArray());
		socket.send(packet);
	}
	
	public String readMessage() throws IOException {
		packet.setData(buff);
		socket.receive(packet);

		ByteArrayInputStream biStream = new ByteArrayInputStream(packet.getData(), 0, packet.getLength());
		DataInputStream diStream = new DataInputStream(biStream);
		
		String msg = diStream.readUTF();
		System.out.println("Ricevuto: " + msg + " (da: "
				+ packet.getAddress().getHostAddress() + ":" + packet.getPort() + ")"
		);
		
		return msg;
	}	
		
	public DatagramSocket getSocket() {
		return socket;
	}
	
	public DatagramPacket getPacket() {
		return packet;
	}

	@Override
	public void close() throws Exception {
		socket.close();
	}
}
