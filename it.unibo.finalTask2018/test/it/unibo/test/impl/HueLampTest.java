package it.unibo.test.impl;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.apache.http.HttpResponse;
import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import it.unibo.finalTask2018.adapter.hueLampAdapter;
import it.unibo.utils.JSONParser;
import it.unibo.utils.RESTfulClient;

public class HueLampTest {

	private static Process lamp;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		lamp = Runtime.getRuntime().exec("java -jar ../HueLampMockServer.jar");
		Thread.sleep(10000);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		if(lamp!=null && lamp.isAlive())
			lamp.destroyForcibly();
	}

	@Test
	public void setUpTest() throws Exception {
		File settings = new File(hueLampAdapter.FILE_NAME);
		if(settings.exists())
			settings.delete();
		
		hueLampAdapter.setUp(null);
		
		try(BufferedReader br = new BufferedReader(new FileReader(hueLampAdapter.FILE_NAME))) {
			String bridge = br.readLine();
			String username = br.readLine();
			String lampID = br.readLine();
			
			assertEquals(hueLampAdapter.DEFAULT_BRIDGE, bridge);
			assertEquals("fnp", username);
			assertEquals("1", lampID);
		}
	}
	
	@Test
	public void turnOnTest() throws Exception  {
		hueLampAdapter.setUp(null);
		hueLampAdapter.setLampState(null, "on");
		HttpResponse response = RESTfulClient.execGET("http://"+hueLampAdapter.DEFAULT_BRIDGE+"/api/fnp/lights/1/state/on");
		JSONObject obj = JSONParser.parseJSONObject(response.getEntity().getContent());
		assertEquals("{\"effect\":\"none\",\"on\":true}", obj.toString());
	}
	
	@Test
	public void turnOffTest() throws Exception  {
		hueLampAdapter.setUp(null);
		hueLampAdapter.setLampState(null, "off");
		HttpResponse response = RESTfulClient.execGET("http://"+hueLampAdapter.DEFAULT_BRIDGE+"/api/fnp/lights/1/state/on");
		JSONObject obj = JSONParser.parseJSONObject(response.getEntity().getContent());
		assertEquals("{\"effect\":\"none\",\"on\":false}", obj.toString());
	}
	
	@Test
	public void blinkTest() throws Exception  {
		hueLampAdapter.setUp(null);
		hueLampAdapter.setLampState(null, "blink");
		HttpResponse response = RESTfulClient.execGET("http://"+hueLampAdapter.DEFAULT_BRIDGE+"/api/fnp/lights/1/state/on");
		JSONObject obj = JSONParser.parseJSONObject(response.getEntity().getContent());
		assertEquals("{\"effect\":\"colorloop\",\"on\":true}", obj.toString());
	}

}
