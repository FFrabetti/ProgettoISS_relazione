package it.unibo.raspRobot;

import java.io.BufferedReader;
import java.io.IOException;

import it.unibo.qactors.akka.QActor;
import it.unibo.sockutils.tcp.TCPClient;

public class robotClient {
	
	private static TCPClient client;
	
	public static void init(QActor qa, String host, String port) throws Exception {
		// "host" dot-notation conversion: addr(X,Y,W,Z) -> X.Y.W.Z
		int index = host.indexOf('(');
		if(index>=0) {
			String inner = host.substring(index+1, host.length()-1); // no ( and )
			host = inner.replace(',','.');
		}
		
		System.out.println("Connecting to: " + host + ":" + port);
		client = new TCPClient(host, port);
		
		new Thread(() -> {
			try {
				BufferedReader br = client.getBufferedReader();
				String line;
				while((line = br.readLine()) != null) {
					System.out.println("emitting frontSonar : sonar(" + line + ")");
					qa.emit("frontSonar", "sonar(" + line + ")");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();
	}
	
	// synchronized: metodo usato sia da realrobotrasp sia da ledagent
	public synchronized static void sendCmd(QActor qa, String cmd) throws IOException {
		client.writeLine(cmd);
	}
	
}
