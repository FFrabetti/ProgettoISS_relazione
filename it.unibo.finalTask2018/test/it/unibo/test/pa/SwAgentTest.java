package it.unibo.test.pa;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import it.unibo.ctxApplPA.MainCtxApplPA;
import it.unibo.qactor.testutils.QATesting;
import it.unibo.qactors.akka.QActor;

public class SwAgentTest extends QATesting {

	private static QActor controller;
	private static QActor swagent;
	private static QActor logger;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		MainCtxApplPA.initTheContext();
		
		controller = waitForQActorToStart("controllerpa");
		swagent = waitForQActorToStart("swagpa");
		logger = waitForQActorToStart("palogger");
		
		// ok modifiche al modello del robot
		controller.addRule("canMove");
		swagent.addRule("isClose");
	}

	@Test
	public void frontSonarCmdEmissionTest() throws Exception {
		Thread.sleep(1000);
		controller.solveGoal("changeModelItem(robot,r1,w(0))");
		controller.emit("frontSonar", "sonar(2)");
		Thread.sleep(400);
		assertTrue(controller.solveGoal("getModelItem(r1,h(X))").isSuccess());
	}

	@Test
	public void sonarSensorCmdEmissionTest() throws Exception {
		Thread.sleep(1000);
		controller.solveGoal("changeModelItem(robot,r1,h(0))");
		controller.emit("sonarSensor", "sonar(sonar1,2)");
		Thread.sleep(400);
		assertTrue(isEventReceived(logger, "ctrlEvent", "ctrlEvent(robot,r1,d(0))"));
	}
	
}
