/* Generated by AN DISI Unibo */ 
package it.unibo.crslogger;
import it.unibo.qactors.QActorContext;
import it.unibo.is.interfaces.IOutputEnvView;
import it.unibo.qactors.akka.QActorMsgQueue;

public class MsgHandle_Crslogger extends QActorMsgQueue{
	public MsgHandle_Crslogger(String actorId, QActorContext myCtx, IOutputEnvView outEnvView )  {
		super(actorId, myCtx, outEnvView);
	}
}
