/* Generated by AN DISI Unibo */ 
package it.unibo.ctxVirtualEnvironment;
import it.unibo.qactors.QActorContext;
import it.unibo.is.interfaces.IOutputEnvView;
import it.unibo.system.SituatedSysKb;
public class MainCtxVirtualEnvironment  {
  
//MAIN
public static QActorContext initTheContext() throws Exception{
	IOutputEnvView outEnvView = SituatedSysKb.standardOutEnvView;
	String webDir = null;
	return QActorContext.initQActorSystem(
		"ctxvirtualenvironment", "./srcMore/it/unibo/ctxVirtualEnvironment/finaltask2018.pl", 
		"./srcMore/it/unibo/ctxVirtualEnvironment/sysRules.pl", outEnvView,webDir,false);
}
public static void main(String[] args) throws Exception{
	QActorContext ctx = initTheContext();
} 	
}