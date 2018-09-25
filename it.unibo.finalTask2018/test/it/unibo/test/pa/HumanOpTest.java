package it.unibo.test.pa;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import it.unibo.ctxApplPA.MainCtxApplPA;
import it.unibo.qactor.testutils.QATesting;
import it.unibo.qactors.akka.QActor;

public class HumanOpTest extends QATesting {

	private static QActor controller;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		MainCtxApplPA.initTheContext();
		controller = waitForQActorToStart("controllerpa");
		controller.addRule("canMove");
	}

	@Test
	public void cmdEmissionTest() throws Exception {
		Thread.sleep(1000);
		controller.emit("usercmd", "usercmd(robotgui(w(0)))");
		Thread.sleep(400);
		assertTrue(controller.solveGoal("getModelItem(r1,w(0))").isSuccess());
		
		controller.emit("usercmd", "usercmd(robotgui(h(0)))");
		Thread.sleep(400);
		assertTrue(controller.solveGoal("getModelItem(r1,h(0))").isSuccess());
	}

}
