/* Generated by AN DISI Unibo */ 
package it.unibo.ctxRealRobotRasp;
import it.unibo.qactors.QActorContext;
import it.unibo.is.interfaces.IOutputEnvView;
import it.unibo.system.SituatedSysKb;
public class MainCtxRealRobotRasp  {
  
//MAIN
public static QActorContext initTheContext() throws Exception{
	IOutputEnvView outEnvView = SituatedSysKb.standardOutEnvView;
	String webDir = null;
	return QActorContext.initQActorSystem(
		"ctxrealrobotrasp", "./srcMore/it/unibo/ctxRealRobotRasp/finaltask2018.pl", 
		"./srcMore/it/unibo/ctxRealRobotRasp/sysRules.pl", outEnvView,webDir,false);
}
public static void main(String[] args) throws Exception{
	QActorContext ctx = initTheContext();
} 	
}
