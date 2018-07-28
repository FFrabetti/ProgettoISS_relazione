package it.unibo.raspRobot.adapter;

import java.io.IOException;
import java.net.UnknownHostException;

import it.unibo.ledrasp.LedRasp;
import it.unibo.qactors.akka.QActor;

public class ledAdapter {
	
	public static void init(QActor qa) throws NumberFormatException, UnknownHostException, IOException {
		LedRasp.init();
	}

	public static void setState(QActor qa, String state) {
		LedRasp.sendLedCmd(state);
	}

}
