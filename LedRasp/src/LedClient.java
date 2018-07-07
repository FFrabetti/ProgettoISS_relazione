import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.UnknownHostException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import it.unibo.ledrasp.LightCmd;
import it.unibo.sockutils.udp.UDPClient;

public class LedClient {

	private static final String DEF_SRV_ADDR = "10.0.3.14";
	private static final String DEF_SRV_PORT = "5555";
	private static final String DEF_ADDR = "10.0.3.3";

	public static void main(String[] args) throws UnknownHostException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		String serverAddr	= args.length>=1 ? args[0] : DEF_SRV_ADDR;
		String serverPort	= args.length>=2 ? args[1] : DEF_SRV_PORT;
		String localAddr	= args.length>=3 ? args[2] : DEF_ADDR;
		
		try(UDPClient client = new UDPClient(localAddr, serverAddr, serverPort)) {
			System.out.println("Led cmd (" + listCmdToStr() + "):");

			String input = null;
			while((input=br.readLine())!=null) {
				client.writeMessage(input);
				System.out.println("Risposta: " + client.readMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String listCmdToStr() {
		return Stream.of(LightCmd.values()).map(c -> c.toString()).collect(Collectors.joining(","));
	}
}
