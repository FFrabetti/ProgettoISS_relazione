package it.unibo.finalTask2018;

import org.apache.http.HttpResponse;
import org.json.JSONObject;

import it.unibo.qactors.akka.QActor;
import it.unibo.utils.RESTfulClient;

public class clientREST {

	public static void sendPut(QActor qa, String url, String key, String value) {
		try {
			JSONObject obj = new JSONObject();
			obj.accumulate(key, value);
			HttpResponse response = RESTfulClient.execPUT(url, obj.toString());
			// debug
			System.out.println(response.getStatusLine());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
