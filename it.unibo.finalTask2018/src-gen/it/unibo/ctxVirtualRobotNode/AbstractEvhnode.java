/* Generated by AN DISI Unibo */ 
package it.unibo.ctxVirtualRobotNode;
import alice.tuprolog.Term;
import it.unibo.contactEvent.interfaces.IEventItem;
import it.unibo.qactors.platform.EventHandlerComponent;
import it.unibo.is.interfaces.IOutputEnvView;
import it.unibo.qactors.QActorContext;
import it.unibo.qactors.QActorUtils;

public abstract class AbstractEvhnode extends EventHandlerComponent { 
protected IEventItem event;
	public AbstractEvhnode(String name, QActorContext myCtx, IOutputEnvView outEnvView, String[] eventIds ) throws Exception {
		super(name, myCtx, eventIds, outEnvView);
  	}
	@Override
	public void doJob() throws Exception {	}
	
	public void handleCurrentEvent() throws Exception {
		event = this.currentEvent; //AKKA getEventItem();
		if( event == null ) return;
		{
		Term msgt       = Term.createTerm(event.getMsg());
		Term msgPattern = Term.createTerm("ctrlEvent(CATEG,NAME,CMD)");
				boolean b = this.pengine.unify(msgt, msgPattern);
				if( b ) {
			  		sendMsg("ctrlMsg","nodebroker", QActorContext.dispatch, msgt.toString() ); 
				}else{
					println("non unifiable");
				}
		}
	}//handleCurrentEvent
	
	@Override
	protected void handleQActorEvent(IEventItem ev) {
		super.handleQActorEvent(ev);
 		try {
			handleCurrentEvent();
		} catch (Exception e) {
 			e.printStackTrace();
		}
	}//handleQActorEvent
	
}
