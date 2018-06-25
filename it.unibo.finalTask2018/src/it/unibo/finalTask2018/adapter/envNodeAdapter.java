package it.unibo.finalTask2018.adapter;

import it.unibo.qactors.akka.QActor;

public class envNodeAdapter {

//	private static final String PATH = "C:/Users/Filippo/Desktop/newVirtual/ConfigurableThreejsApp";
	private static final String PATH = "../nodeServer/ConfigurableThreejsApp";
	
	public static void setUpEnvironment(QActor qa) throws Exception {
		System.out.println("setting up the environment...");

		/* startServerPath.bat
		 * ----------------------------
		 * if NOT "%1" == "" cd %1
		 * cd server
		 * cd src
		 * start cmd /k node main 8999
		 */
		Runtime.getRuntime().exec(PATH + "/startServerPath.bat " + PATH); // + " > test.txt 2>&1");
	}

}
