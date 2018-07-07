package it.unibo.ledrasp;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.function.UnaryOperator;

import it.unibo.sockutils.udp.UDPServer;
import it.unibo.sockutils.udp.UDPServerThread;

public class LedRasp {

	private static final String FILE_CMD = LedRasp.class.getSimpleName() + "_cmd.txt";
	
	private static final String DEF_ADDR = "10.0.3.14";
	private static final String DEF_PORT = "5555";
	
	public static void main(String[] args) throws IOException {
		Map<String,String> map = new HashMap<>();
		
		try(Reader reader = new FileReader(FILE_CMD)) {
			BufferedReader br = new BufferedReader(reader);
			String cmd = null;
			String exec = null;
			
			while((cmd=br.readLine())!=null && (exec=br.readLine())!=null) {
				map.put(cmd, exec);
				System.out.println("Map put: " + cmd + " -> " + exec);
			}
		} catch (FileNotFoundException e) {
			new FileWriter(FILE_CMD).close();
			System.out.println("Created file: " + FILE_CMD);
			System.exit(-1);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
//		TCPServer server = new TCPServer(args.length==1 ? args[0] : "5555", new LampAgent(map));
//		server.startServer();

		String localAddr = args.length>=1 ? args[0] : DEF_ADDR;
		String localPort = args.length>=2 ? args[1] : DEF_PORT;
		
		UDPServer server = new UDPServer(localAddr, localPort);
		UDPServerThread sT = new UDPServerThread(server, new LampAgent(map));
		sT.start();
	}

	private static class LampAgent implements UnaryOperator<String> {

		private LightCmd status = LightCmd.OFF;
		private Process execP;
		private Map<String,String> map;
		
		public LampAgent(Map<String,String> map) {
			this.map = map;
		}
		
		@Override
		public String apply(String strCmd) {
			try {
				LightCmd cmd = LightCmd.valueOf(strCmd.toUpperCase());

				if (cmd != status) {
					if (execP != null && execP.isAlive()) {
						execP.destroy();
						if(execP.isAlive())
							execP.destroyForcibly();
					}
					
					System.out.println("Executing: " + map.get(strCmd));
					execP = Runtime.getRuntime().exec(map.get(strCmd));
					status = cmd;
				}

				return cmd.toString();
			} catch (Exception e) {
				e.printStackTrace();
				return "error";
			}
		}
	}
}
