/* Generated by AN DISI Unibo */ 
package it.unibo.ctxRealRobotMock;
import it.unibo.qactors.QActorContext;
import it.unibo.is.interfaces.IOutputEnvView;
import it.unibo.system.SituatedSysKb;
public class MainCtxRealRobotMock  {
  
//MAIN
public static QActorContext initTheContext() throws Exception{
	IOutputEnvView outEnvView = SituatedSysKb.standardOutEnvView;
	String webDir = null;
	return QActorContext.initQActorSystem(
		"ctxrealrobotmock", "./srcMore/it/unibo/ctxRealRobotMock/finaltask2018.pl", 
		"./srcMore/it/unibo/ctxRealRobotMock/sysRules.pl", outEnvView,webDir,false);
}
public static void main(String[] args) throws Exception{
	QActorContext ctx = initTheContext();
} 	
}