package it.unibo.test.pa;

import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import alice.tuprolog.SolveInfo;
import it.unibo.ctxApplPA.MainCtxApplPA;
import it.unibo.qactor.testutils.QATesting;
import it.unibo.qactors.akka.QActor;

public class ControllerTest extends QATesting {

	private static QActor controller;
	private static QActor logger;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		MainCtxApplPA.initTheContext();
		
		controller = waitForQActorToStart("controllerpa");
		logger = waitForQActorToStart("palogger");
	}

	@Test
	public void changeGetModelItemTest() throws Exception {
		String t = "10";
		contrChangeModel("temperature,t1," + t);
		assertTrue(contrGetModel("t1," + t).isSuccess());

		String h = "13";
		String m = "30";
		contrChangeModel("clock,c1,h(" + h + "," + m + ")");
		assertTrue(contrGetModel("c1,h(" + h + "," + m + ")").isSuccess());
	}
	
	@Test
	public void overLimitTempTest() throws Exception {
		contrChangeModel("robot,r1,w(0)");
		assertTrue(contrGetModel("r1,w(0)").isSuccess());
		
		String t = contrSolve("limitTemperatureValue(T)").getTerm("T").toString();
		contrChangeModel("temperature,t1," + t);
		assertTrue(contrGetModel("r1,h(X)").isSuccess());
	}

	@Test
	public void outOfBoundaryTimeTest() throws Exception {
		contrChangeModel("robot,r1,w(0)");
		assertTrue(contrGetModel("r1,w(0)").isSuccess());
		
		SolveInfo si = contrSolve("timeInterval(h(_,_),h(H,M))");
		String h = si.getTerm("H").toString();
		String m = si.getTerm("M").toString();
		contrChangeModel("clock,c1,h(" + h + "," + m + ")");
		assertTrue(contrGetModel("r1,h(X)").isSuccess());
	}
	
	@Test
	public void changeLedStateTest() throws Exception {
		contrChangeModel("robot,r1,h(0)");
		contrChangeModel("robot,r1,w(0)");
		assertTrue(contrGetModel("l1,blink").isSuccess());
		
		contrChangeModel("robot,r1,h(0)");
		assertTrue(contrGetModel("l1,off").isSuccess());
	}
	
	@Test
	public void leftRightTurnTest() throws Exception {
		contrChangeModel("robot,r1,a(0)");
		assertTrue(contrGetModel("r1,h(X)").isSuccess());
		
		contrChangeModel("robot,r1,d(0)");
		assertTrue(contrGetModel("r1,h(X)").isSuccess());
	}
	
	@Test
	public void robotCanMoveTest() throws Exception {
		boolean canMove = contrSolve("canMove").isSuccess();
		if(!canMove)
			controller.addRule("canMove");
		contrChangeModel("robot,r1,h(0)");
		contrSolve("changeRobotModel(w(0))");
		assertTrue(contrGetModel("r1,w(X)").isSuccess());
		if(!canMove)
			controller.removeRule("canMove");
		
		if(contrSolve("canMove").isSuccess()) {
			// temperatura non valida
			String t = contrSolve("limitTemperatureValue(T)").getTerm("T").toString();
			contrChangeModel("temperature,t1," + t);
		}		
		contrChangeModel("robot,r1,h(0)");
		contrSolve("changeRobotModel(w(0))");
		assertTrue(contrGetModel("r1,h(X)").isSuccess());
	}
	
	@Test
	public void ctrlEventRobotEmissionTest() throws Exception {
		contrChangeModel("robot,r1,w(0)");
		contrChangeModel("robot,r1,h(0)");
		Thread.sleep(1000);
		assertTrue(isEventReceived(logger, "ctrlEvent", "ctrlEvent(robot,r1,h(0))"));
	}
	
	@Test
	public void lightCmdEmissionTest() throws Exception {
		contrChangeModel("led,l1,on");
		contrChangeModel("led,l1,off");
		Thread.sleep(1000);
		assertTrue(isEventReceived(logger, "lightCmd", "lightCmd(off)"));
	}

	@Test
	public void inputCtrlEventReceptionTest() throws Exception {
		contrChangeModel("led,l1,off");
		controller.emit("inputCtrlEvent", "inputEvent(led,l1,on)");
		Thread.sleep(1000);
		assertTrue(contrGetModel("l1,on").isSuccess());
	}
	
	@Test
	public void cmdMsgReceptionTest() throws Exception {
		contrChangeModel("robot,r1,h(0)");
		controller.addRule("canMove");
		
		sendMsg(logger, controller, "cmd", "cmd(w(0))");
		Thread.sleep(1000);

		assertTrue(contrGetModel("r1,w(0)").isSuccess());
		controller.removeRule("canMove");
	}
	
	// utilities
	private static SolveInfo contrSolve(String goal) {
		return controller.solveGoal(goal);
	}
	
	private static SolveInfo contrGetModel(String s) {
		return contrSolve("getModelItem(" + s + ")");
	}
	
	private static SolveInfo contrChangeModel(String s) {
		return contrSolve("changeModelItem(" + s + ")");
	}
	
}
