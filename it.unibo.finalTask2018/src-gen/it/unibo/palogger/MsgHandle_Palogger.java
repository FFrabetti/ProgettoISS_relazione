/* Generated by AN DISI Unibo */ 
package it.unibo.palogger;
import it.unibo.qactors.QActorContext;
import it.unibo.is.interfaces.IOutputEnvView;
import it.unibo.qactors.akka.QActorMsgQueue;

public class MsgHandle_Palogger extends QActorMsgQueue{
	public MsgHandle_Palogger(String actorId, QActorContext myCtx, IOutputEnvView outEnvView )  {
		super(actorId, myCtx, outEnvView);
	}
}
