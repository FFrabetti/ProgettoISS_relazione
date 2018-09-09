package it.unibo.finalTask2018.adapter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import it.unibo.qactors.akka.QActor;
import it.unibo.utils.JSONParser;
import it.unibo.utils.RESTfulClient;

public class hueLampAdapter {

	private static String bridge;
	private static String lamp;
	private static String username;
	
	public static final String FILE_NAME = "lamp_settings.txt";
	public static final String DEFAULT_BRIDGE = "localhost:8666";

	public static void setUp(QActor qa) throws ClientProtocolException, IOException {
		File f = new File(FILE_NAME);
		if (f.exists()) { // controllo esistenza file
			BufferedReader br = new BufferedReader(new FileReader(f));
			bridge = br.readLine();
			username = br.readLine();
			lamp = br.readLine();
			br.close();
			if (checkBridge()) { // controllo bridge
				if(!checkLamp()) { // lamp non valida, ne cerco un'altra
					findLamp();
					updateSettingsFile(f);
				}
			} else { // bridge non valido, cerco ip bridge ed id lamp
				findBridge();
				getUsername();
				findLamp();
				updateSettingsFile(f);
			}
		} else { // file non trovato, cerco ip bridge ed id lamp
			f.createNewFile();
			findBridge();
			getUsername();
			findLamp();
			updateSettingsFile(f);
		}
	}

	public static void setLampState(QActor qa, String state) throws ClientProtocolException, IOException {
		JSONObject obj = new JSONObject();
		HttpResponse response = null;
		if (state.equals("on")) {
			obj.accumulate("on", true);
			obj.accumulate("effect", "none");
			response = RESTfulClient.execPUT("http://" + bridge + "/api/" + username + "/lights/" + lamp + "/state",
					obj.toString());
		} else if (state.equals("off")) {
			obj.accumulate("on", false);
			obj.accumulate("effect", "none");
			response = RESTfulClient.execPUT("http://" + bridge + "/api/" + username + "/lights/" + lamp + "/state",
					obj.toString());
		} else if (state.equals("blink")) {
			obj.accumulate("on", true);
			obj.accumulate("effect", "colorloop"); // -> multicolor loop :)
			response = RESTfulClient.execPUT("http://" + bridge + "/api/" + username + "/lights/" + lamp + "/state",
					obj.toString());
		}
		JSONParser.parseJSONArray(response.getEntity().getContent());
	}


	private static void findBridge() throws ClientProtocolException, IOException {
		try {
			HttpResponse response = RESTfulClient.execGET("https://discovery.meethue.com");
			JSONArray arr = JSONParser.parseJSONArray(response.getEntity().getContent());
			bridge = arr.getJSONObject(0).getString("internalipaddress");
		}
		catch(Exception e) {
			bridge = DEFAULT_BRIDGE;
		}
	}

	private static boolean checkBridge() throws ClientProtocolException, IOException {
		boolean check;
		HttpResponse response = RESTfulClient.execGET("http://" + bridge + "/api/valereusa/config");
		JSONArray arr = JSONParser.parseJSONArray(response.getEntity().getContent());
		try {
			arr.getJSONObject(0).getString("name");
			check = true;
		} catch (JSONException e) {
			check = false;
		}
		return check;
	}
	
	private static void findLamp() throws ClientProtocolException, IOException {
		HttpResponse response = RESTfulClient.execGET("http://" + bridge + "/api/" + username + "/lights");
		JSONObject obj = JSONParser.parseJSONObject(response.getEntity().getContent());
		lamp = obj.keySet().toArray()[0].toString();
	}
	
	private static boolean checkLamp() throws ClientProtocolException, IOException {
		HttpResponse response = RESTfulClient.execGET("http://" + bridge + "/api/" + username + "/lights/" + lamp);
		JSONObject obj = JSONParser.parseJSONObject(response.getEntity().getContent());
		return !(obj.isEmpty());
	}

	private static void getUsername() throws ClientProtocolException, IOException {
		JSONObject obj = new JSONObject();
		obj.accumulate("devicetype", "finalTask#pc"); // <application_name>#<device_name>
		HttpResponse response = RESTfulClient.execPOST("http://" + bridge + "/api", obj.toString());
		JSONArray arr = JSONParser.parseJSONArray(response.getEntity().getContent());
		username = arr.getJSONObject(0).getJSONObject("success").getString("username");
	}
	
	private static void updateSettingsFile(File settings) throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter(settings));
		bw.write(bridge); bw.newLine();
		bw.write(username); bw.newLine();
		bw.write(lamp); bw.newLine();
		bw.close();
	}

}
