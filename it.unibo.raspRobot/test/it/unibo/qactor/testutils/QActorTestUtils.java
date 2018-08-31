package it.unibo.qactor.testutils;

import it.unibo.qactors.QActorUtils;
import it.unibo.qactors.akka.QActor;

public class QActorTestUtils {

	public static String getActorName(String name) {
		return name + "_ctrl";
	}

	public static QActor waitForQActorToStart(String name) {
		QActor qa = null;

		try {
			while (qa == null) {
				Thread.sleep(500);
				qa = QActorUtils.getQActor(getActorName(name));
			}
		} catch (InterruptedException e) {
			if (qa == null)
				throw new IllegalStateException("qa == null");
		}

		return qa;
	}

	public static boolean isGoalSuccess(QActor qa, String goal) {
		return qa.solveGoal(goal).isSuccess();
	}

	public static boolean isEventReceived(QActor qa, String evname, String evpl) {
		return isGoalSuccess(qa, "logevent(" + evname + "," + evpl + ")");
	}

	public static boolean isMsgReceived(QActor qa, String msgname, String msgpl) {
		return isGoalSuccess(qa, "logmsg(" + msgname + "," + msgpl + ")");
	}
	
	public static void waitForTestEnd(QActor tester) {
		try {
			while (!isGoalSuccess(tester, "test(end)"))
				Thread.sleep(500);
		} catch (InterruptedException e) {
			if (!isGoalSuccess(tester, "test(end)"))
				throw new IllegalStateException("not isGoalSuccess(tester, test(end))");
		}
	}
	
	/*
	 * qa.sendMsg(String msgID, String dest, String msgType, String msg)
	 * 		humanoperatorra.sendMsg("cmd", "applra", "dispatch", "cmd(d(1))");
	 * qa.sendMsg(String senderName, String msgID, String dest, String msgType, String msg)
	 * 		humanoperatorra.sendMsg("humanoperatorra_ctrl", "cmd", "applra", "dispatch", "cmd(d(2))");
	 */
	public static void sendMsg(QActor sender, String dest, String msgname, String msgpl) throws Exception {
		sender.sendMsg(msgname, dest, "dispatch", msgpl);
	}
	
	public static void sendMsg(QActor sender, QActor dest, String msgname, String msgpl) throws Exception {
		sendMsg(sender, dest.getNameNoCtrl(), msgname, msgpl);
	}
	
}
