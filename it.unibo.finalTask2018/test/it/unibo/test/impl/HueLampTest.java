package it.unibo.test.impl;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import it.unibo.finalTask2018.adapter.hueLampAdapter;

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

}
