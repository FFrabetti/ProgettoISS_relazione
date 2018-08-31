package it.unibo.test.ra;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import it.unibo.finalTask2018.robot.ledMock.ILed;
import it.unibo.finalTask2018.robot.ledMock.LedMockGui;

public class LedTest {

	private static ILed led;
	
	@BeforeClass
	public static void setUpBeforeClass() {
		led = new LedMockGui();
	}
	
	@Test
	public void turnOnTest() {
		led.turnOff();
		led.turnOn();
		Assert.assertTrue(led.isOn());
	}

	@Test
	public void turnOffTest() {
		led.turnOn();
		led.turnOff();
		Assert.assertFalse(led.isOn());
	}
	
}
