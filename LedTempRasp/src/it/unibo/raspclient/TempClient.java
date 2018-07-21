package it.unibo.raspclient;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;

import it.unibo.sockutils.udp.UDPClient;

public class TempClient {

	private static final String DEF_SRV_ADDR = "10.0.3.14";
	private static final String DEF_SRV_PORT = "6666";
	private static final String DEF_ADDR = "10.0.3.3";
	
	private static double currTemp = 0;
	private static UDPClient client;
	
	public static double getTemperature() throws IOException {
		client.writeMessage("temp");
		String temp = client.readMessage();
		
		currTemp = Double.parseDouble(temp);
		System.out.println("T = " + currTemp);
		return currTemp;
	}
	
	public static void init(String[] args) throws NumberFormatException, UnknownHostException, IOException {
		String serverAddr	= args.length>=1 ? args[0] : DEF_SRV_ADDR;
		String serverPort	= args.length>=2 ? args[1] : DEF_SRV_PORT;
		String localAddr	= args.length>=3 ? args[2] : DEF_ADDR;

		client = new UDPClient(localAddr, serverAddr, serverPort);
	}
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		init(args);
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		System.out.println("Premi invio:");
		try {
			String input = null;
			while((input=br.readLine())!=null) {
				client.writeMessage(input);
				String temp = client.readMessage();
				
				try {
					currTemp = Double.parseDouble(temp);
					System.out.println("T = " + currTemp);
				} catch(Exception e) {
					System.out.println("error parseDouble: " + temp);
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
