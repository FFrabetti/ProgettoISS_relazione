package it.unibo.qactor.testutils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import it.unibo.qactors.QActorUtils;
import it.unibo.qactors.akka.QActor;

public class QATesting {

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
	
	public static Process execMain(Class<?> klass) throws IOException, InterruptedException {
		String javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
		String classpath = System.getProperty("java.class.path");
		String className = klass.getCanonicalName();

		ProcessBuilder builder = new ProcessBuilder(javaBin, "-cp", classpath, className);
		builder.redirectErrorStream();
		Process process = builder.start();

		printOutputThread(process, "out_" + className);
		
		return process;
		// process.waitFor();
		// return process.exitValue();
	}

	private static Thread printOutputThread(Process process, String fileName) {
		InputStream input = process.getInputStream();
		
		Thread t = new Thread(() -> {
			try (PrintWriter pw = new PrintWriter(new File(fileName))) {
				BufferedReader br = new BufferedReader(new InputStreamReader(input));
				String line;
				while (process.isAlive() && (line = br.readLine()) != null) {
					pw.write(line + "\n");
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			finally {
				System.out.println("Process output in file: " + fileName);
			}
		});
		t.start();
		
		return t;
	}
	
	public static void stopProcess(Process process) throws InterruptedException {
		if(process!=null && process.isAlive()) {
			process.destroyForcibly();
			process.waitFor();
		}
	}
	
}
