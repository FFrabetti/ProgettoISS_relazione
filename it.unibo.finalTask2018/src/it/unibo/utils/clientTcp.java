package it.unibo.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import org.json.JSONObject;
import it.unibo.qactors.akka.QActor;

public class clientTcp {

	private static final int MOVE_DURATION = 800;
	private static final String HOST = "localhost";
	private static final int PORT = 8999;
	private static final String SEP = ";";

	protected static Socket clientSocket;
	protected static PrintWriter outToServer;
	protected static BufferedReader inFromServer;
	protected static Thread rdThread;

	public static void initClientConn(QActor qa) throws Exception {
		initClientConn(qa, HOST, PORT);
	}

	public static void initClientConn(QActor qa, String hostName, int port) throws Exception {
		clientSocket = new Socket(hostName, port);
		// outToServer = new DataOutputStream(clientSocket.getOutputStream());
		// //DOES NOT WORK!!!!;
		inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		outToServer = new PrintWriter(clientSocket.getOutputStream());

		startTheReader(qa);
	}

	public static void sendMsg(QActor qa, String jsonString) throws Exception {
		JSONObject jsonObject = new JSONObject(jsonString);
		String msg = SEP + jsonObject.toString() + SEP;
		outToServer.println(msg);
		outToServer.flush();
	}

	// "arg" is the duration in seconds of the movement.
	// The duration can be negative, in that case the player will move until it
	// encounters an obstacle.
	public static void sendCmd(QActor qa, String msg, int arg) {
		try {
			String jsonString = "{ 'type': '" + msg + "', 'arg': " + arg + " }";
			sendMsg(qa, jsonString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void sendCmd(QActor qa, String msg) {
		sendCmd(qa, msg, MOVE_DURATION);
	}

	protected static void startTheReader(QActor qa) {
		if (rdThread != null && rdThread.isAlive())
			rdThread.interrupt();

		rdThread = new ReaderThread(qa);
		rdThread.start();
	}

	// public void doJob() throws Exception {
	// initClientConn();
	// String jsonString ="";
	// JSONObject jsonObject;
	// String msg="";
	// for( int i=1; i<=3; i++ ) {
	// jsonString = "{ 'type': 'moveForward', 'arg': 800 }";
	// jsonObject = new JSONObject(jsonString);
	// msg = sep+jsonObject.toString()+sep;
	// println("sending msg=" + msg);
	// sendMsg( msg );
	// Thread.sleep(1000);
	//
	// jsonString = "{ 'type': 'moveBackward', 'arg': 800 }";
	// jsonObject = new JSONObject(jsonString);
	// msg = sep+jsonObject.toString()+sep;
	// println("sending msg=" + msg);
	// sendMsg( msg );
	// Thread.sleep(1000);
	//
	// }
	// clientSocket.close();
	// }
	//
	//
	// public static void main(String[] args) throws Exception {
	// new ClientTcp().doJob();
	// }

	private static class ReaderThread extends Thread {

		private QActor qa;

		public ReaderThread(QActor qa) {
			this.qa = qa;
		}

		/*
		 * This message is sent by the server when a sonar is sensing the
		 * player. sonarName is the name of the sonar that is sensing the
		 * player. distance is the distance of the player from the sonar. axis
		 * is the axis on which the sonar is sensing the player.
		 */
		// Event sonarSensor : sonar(NAME, DISTANCE)
		private void sonarActivated(String sonar, int distance) {
			qa.emit("sonarSensor", "sonar(" + sonar + "," + distance + ")");
		}

		/*
		 * This message is sent by the server when the player collides with an
		 * obstacle in the scene. objectName is the name of the object the
		 * player is colliding with.
		 */
		// Event frontSonar : sonar( DISTANCE )
		private void collision(String obstacle) {
			qa.emit("frontSonar", "sonar(2)");
		}

		@Override
		public void run() {
			String inpuStr;
			try {
				// while (true) {
				while ((inpuStr = inFromServer.readLine()) != null) {
					// System.out.println( "reads: " + inpuStr);
					String jsonMsgStr = inpuStr.split(";")[1];
					// System.out.println( "reads: " + jsonMsgStr + " qa=" +
					// qa.getName() );
					JSONObject jsonObject = new JSONObject(jsonMsgStr);
					// System.out.println( "type: " +
					// jsonObject.getString("type"));
					switch (jsonObject.getString("type")) {
					case "webpage-ready":
						System.out.println("webpage-ready ");
						break;
					case "sonar-activated": {
						// wSystem.out.println( "sonar-activated " );
						JSONObject jsonArg = jsonObject.getJSONObject("arg");
						String sonarName = jsonArg.getString("sonarName");
						int distance = jsonArg.getInt("distance");
						// System.out.println( "sonarName=" + sonarName + "
						// distance=" + distance);
						sonarActivated(sonarName.replace("-", ""), distance);
						break;
					}
					case "collision": {
						// System.out.println( "collision" );
						JSONObject jsonArg = jsonObject.getJSONObject("arg");
						String objectName = jsonArg.getString("objectName");
						// System.out.println( "collision objectName=" +
						// objectName );
						collision(objectName.replace("-", ""));
						break;
					}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
