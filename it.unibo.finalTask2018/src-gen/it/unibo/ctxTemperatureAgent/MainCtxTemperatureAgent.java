/* Generated by AN DISI Unibo */ 
package it.unibo.ctxTemperatureAgent;
import it.unibo.qactors.QActorContext;
import it.unibo.is.interfaces.IOutputEnvView;
import it.unibo.system.SituatedSysKb;
public class MainCtxTemperatureAgent  {
  
//MAIN
public static QActorContext initTheContext() throws Exception{
	IOutputEnvView outEnvView = SituatedSysKb.standardOutEnvView;
	String webDir = null;
	return QActorContext.initQActorSystem(
		"ctxtemperatureagent", "./srcMore/it/unibo/ctxTemperatureAgent/finaltask2018.pl", 
		"./srcMore/it/unibo/ctxTemperatureAgent/sysRules.pl", outEnvView,webDir,false);
}
public static void main(String[] args) throws Exception{
	QActorContext ctx = initTheContext();
} 	
}
