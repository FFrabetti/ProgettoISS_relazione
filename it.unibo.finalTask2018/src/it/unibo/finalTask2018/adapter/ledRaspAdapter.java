package it.unibo.finalTask2018.adapter;

import java.io.IOException;
import java.net.UnknownHostException;

import it.unibo.qactors.akka.QActor;
import it.unibo.sockutils.udp.UDPClient;

public class ledRaspAdapter {

	private static final String DEF_SRV_ADDR = "10.0.3.14";
	private static final String DEF_SRV_PORT = "5555";
	private static final String DEF_ADDR = "10.0.3.3";
	
	private static UDPClient client;
	
	public static void init(QActor qa) throws NumberFormatException, UnknownHostException, IOException {
		client = new UDPClient(DEF_ADDR, DEF_SRV_ADDR, DEF_SRV_PORT);
	}

	public static void setLamp(QActor qa, String cmd) throws IOException {
		client.writeMessage(cmd);
		System.out.println("Risposta da LedRasp: " + client.readMessage());
	}
	
}
