/* Generated by AN DISI Unibo */ 
package it.unibo.ctxHumanOperator;
import it.unibo.qactors.QActorContext;
import it.unibo.is.interfaces.IOutputEnvView;
import it.unibo.system.SituatedSysKb;
public class MainCtxHumanOperator  {
  
//MAIN
public static QActorContext initTheContext() throws Exception{
	IOutputEnvView outEnvView = SituatedSysKb.standardOutEnvView;
	String webDir = null;
	return QActorContext.initQActorSystem(
		"ctxhumanoperator", "./srcMore/it/unibo/ctxHumanOperator/finaltask2018.pl", 
		"./srcMore/it/unibo/ctxHumanOperator/sysRules.pl", outEnvView,webDir,false);
}
public static void main(String[] args) throws Exception{
	QActorContext ctx = initTheContext();
} 	
}