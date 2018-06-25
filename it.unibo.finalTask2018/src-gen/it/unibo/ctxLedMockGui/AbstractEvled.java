/* Generated by AN DISI Unibo */ 
package it.unibo.ctxLedMockGui;
import alice.tuprolog.Term;
import it.unibo.contactEvent.interfaces.IEventItem;
import it.unibo.qactors.platform.EventHandlerComponent;
import it.unibo.is.interfaces.IOutputEnvView;
import it.unibo.qactors.QActorContext;
import it.unibo.qactors.QActorUtils;

public abstract class AbstractEvled extends EventHandlerComponent { 
protected IEventItem event;
	public AbstractEvled(String name, QActorContext myCtx, IOutputEnvView outEnvView, String[] eventIds ) throws Exception {
		super(name, myCtx, eventIds, outEnvView);
  	}
	@Override
	public void doJob() throws Exception {	}
	
	public void handleCurrentEvent() throws Exception {
		event = this.currentEvent; //AKKA getEventItem();
		if( event == null ) return;
		//RaiseOtherEvent
		{String newcontent = "blinkCmd(on)";
		newcontent =  updateVars( Term.createTerm("ctrlEvent(CATEG,NAME,blink)"), 
			                Term.createTerm("ctrlEvent(CATEG,NAME,blink)"), 
			                Term.createTerm( event.getMsg() ), newcontent);
		//println("newcontent="+newcontent);
		if( newcontent != null ){ emit( "blinkCmd", newcontent ); }
		}
		//RaiseOtherEvent
		{String newcontent = "blinkCmd(off)";
		newcontent =  updateVars( Term.createTerm("ctrlEvent(CATEG,NAME,noblink)"), 
			                Term.createTerm("ctrlEvent(CATEG,NAME,noblink)"), 
			                Term.createTerm( event.getMsg() ), newcontent);
		//println("newcontent="+newcontent);
		if( newcontent != null ){ emit( "blinkCmd", newcontent ); }
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
