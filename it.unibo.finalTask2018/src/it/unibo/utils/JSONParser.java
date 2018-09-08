package it.unibo.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONParser {

	public static JSONObject parseJSONObject(InputStream input) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(input));
		String line;
		StringBuilder sb = new StringBuilder();
		while ((line = br.readLine()) != null) {
			sb.append(line);
		}
		try {
			return new JSONObject(sb.toString());
		} catch (JSONException e) {
			return null;
		}
	}

	public static JSONArray parseJSONArray(InputStream input) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(input));
		String line;
		StringBuilder sb = new StringBuilder();
		while ((line = br.readLine()) != null) {
			sb.append(line);
		}
		try {
			return new JSONArray(sb.toString());
		} catch (JSONException e) {
			return null;
		}
	}

}
