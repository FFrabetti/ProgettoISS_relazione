package test;

import main.HueLampAgent;
import utils.JSONParser;
import utils.RESTfulClient;

public class TestRESTfulApi {

	public static void main(String[] args) {
		try {
//			// TEST 1: provo una alla volta tutte le funzioni (ricorda di metterle public
//			//			e di settare l'ip del bridge)
//			HueLampAgent.checkBridge();
//			HueLampAgent.getUsername();
//			HueLampAgent.findLamp();
//			HueLampAgent.checkLamp();
//			HueLampAgent.updateSettingsFile(new File("lamp_settings.txt"));
			
			// TEST 2: con il file lamp_settings.txt già pronto, provo a vedere se il setUp() rapido funziona
			HueLampAgent.setUp();
			
			// TEST 3: cambaire stato alla lampada
			HueLampAgent.setLampState("on");
			System.out.println(JSONParser.parseJSONObject(RESTfulClient.execGET("http://localhost:8666/api/fnp/lights/1/state/on").getEntity().getContent()));
			System.out.println();
			HueLampAgent.setLampState("off");
			System.out.println(JSONParser.parseJSONObject(RESTfulClient.execGET("http://localhost:8666/api/fnp/lights/1/state/on").getEntity().getContent()));
			System.out.println();
			HueLampAgent.setLampState("blink");
			System.out.println(JSONParser.parseJSONObject(RESTfulClient.execGET("http://localhost:8666/api/fnp/lights/1/state/on").getEntity().getContent()));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
