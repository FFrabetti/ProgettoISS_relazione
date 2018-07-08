/* Generated by AN DISI Unibo */ 
package it.unibo.ctxLedRasp;
import it.unibo.qactors.QActorContext;
import it.unibo.is.interfaces.IOutputEnvView;
import it.unibo.system.SituatedSysKb;
public class MainCtxLedRasp  {
  
//MAIN
public static QActorContext initTheContext() throws Exception{
	IOutputEnvView outEnvView = SituatedSysKb.standardOutEnvView;
	String webDir = null;
	return QActorContext.initQActorSystem(
		"ctxledrasp", "./srcMore/it/unibo/ctxLedRasp/ledrasp.pl", 
		"./srcMore/it/unibo/ctxLedRasp/sysRules.pl", outEnvView,webDir,false);
}
public static void main(String[] args) throws Exception{
	QActorContext ctx = initTheContext();
} 	
}