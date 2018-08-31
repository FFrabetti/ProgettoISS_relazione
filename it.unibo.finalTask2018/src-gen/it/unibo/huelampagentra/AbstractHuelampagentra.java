/* Generated by AN DISI Unibo */ 
package it.unibo.huelampagentra;
import it.unibo.qactors.PlanRepeat;
import it.unibo.qactors.QActorContext;
import it.unibo.qactors.StateExecMessage;
import it.unibo.qactors.QActorUtils;
import it.unibo.is.interfaces.IOutputEnvView;
import it.unibo.qactors.action.AsynchActionResult;
import it.unibo.qactors.action.IActorAction;
import it.unibo.qactors.action.IActorAction.ActionExecMode;
import it.unibo.qactors.action.IMsgQueue;
import it.unibo.qactors.akka.QActor;
import it.unibo.qactors.StateFun;
import java.util.Stack;
import java.util.Hashtable;
import java.util.concurrent.Callable;
import alice.tuprolog.Struct;
import alice.tuprolog.Term;
import it.unibo.qactors.action.ActorTimedAction;
public abstract class AbstractHuelampagentra extends QActor { 
	protected AsynchActionResult aar = null;
	protected boolean actionResult = true;
	protected alice.tuprolog.SolveInfo sol;
	protected String planFilePath    = null;
	protected String terminationEvId = "default";
	protected String parg="";
	protected boolean bres=false;
	protected IActorAction action;
	 
	
		protected static IOutputEnvView setTheEnv(IOutputEnvView outEnvView ){
			return outEnvView;
		}
		public AbstractHuelampagentra(String actorId, QActorContext myCtx, IOutputEnvView outEnvView )  throws Exception{
			super(actorId, myCtx,  
			"./srcMore/it/unibo/huelampagentra/WorldTheory.pl",
			setTheEnv( outEnvView )  , "init");
			this.planFilePath = "./srcMore/it/unibo/huelampagentra/plans.txt";
	  	}
		@Override
		protected void doJob() throws Exception {
			String name  = getName().replace("_ctrl", "");
			mysupport = (IMsgQueue) QActorUtils.getQActor( name ); 
			initStateTable(); 
	 		initSensorSystem();
	 		history.push(stateTab.get( "init" ));
	  	 	autoSendStateExecMsg();
	  		//QActorContext.terminateQActorSystem(this);//todo
		} 	
		/* 
		* ------------------------------------------------------------
		* PLANS
		* ------------------------------------------------------------
		*/    
	    //genAkkaMshHandleStructure
	    protected void initStateTable(){  	
	    	stateTab.put("handleToutBuiltIn",handleToutBuiltIn);
	    	stateTab.put("init",init);
	    	stateTab.put("waitForEvents",waitForEvents);
	    	stateTab.put("commandLamp",commandLamp);
	    }
	    StateFun handleToutBuiltIn = () -> {	
	    	try{	
	    		PlanRepeat pr = PlanRepeat.setUp("handleTout",-1);
	    		String myselfName = "handleToutBuiltIn";  
	    		println( "huelampagentra tout : stops");  
	    		repeatPlanNoTransition(pr,myselfName,"application_"+myselfName,false,false);
	    	}catch(Exception e_handleToutBuiltIn){  
	    		println( getName() + " plan=handleToutBuiltIn WARNING:" + e_handleToutBuiltIn.getMessage() );
	    		QActorContext.terminateQActorSystem(this); 
	    	}
	    };//handleToutBuiltIn
	    
	    StateFun init = () -> {	
	    try{	
	     PlanRepeat pr = PlanRepeat.setUp("init",-1);
	    	String myselfName = "init";  
	    	temporaryStr = "\"hueLampAgent start\"";
	    	println( temporaryStr );  
	    	//switchTo waitForEvents
	        switchToPlanAsNextState(pr, myselfName, "huelampagentra_"+myselfName, 
	              "waitForEvents",false, false, null); 
	    }catch(Exception e_init){  
	    	 println( getName() + " plan=init WARNING:" + e_init.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//init
	    
	    StateFun waitForEvents = () -> {	
	    try{	
	     PlanRepeat pr = PlanRepeat.setUp(getName()+"_waitForEvents",0);
	     pr.incNumIter(); 	
	    	String myselfName = "waitForEvents";  
	    	temporaryStr = "\"hueLampAgent waiting for events\"";
	    	println( temporaryStr );  
	    	//bbb
	     msgTransition( pr,myselfName,"huelampagentra_"+myselfName,false,
	          new StateFun[]{stateTab.get("commandLamp") }, 
	          new String[]{"true","E","lightCmd" },
	          3600000, "handleToutBuiltIn" );//msgTransition
	    }catch(Exception e_waitForEvents){  
	    	 println( getName() + " plan=waitForEvents WARNING:" + e_waitForEvents.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//waitForEvents
	    
	    StateFun commandLamp = () -> {	
	    try{	
	     PlanRepeat pr = PlanRepeat.setUp("commandLamp",-1);
	    	String myselfName = "commandLamp";  
	    	//onEvent 
	    	setCurrentMsgFromStore(); 
	    	curT = Term.createTerm("lightCmd(blink)");
	    	if( currentEvent != null && currentEvent.getEventId().equals("lightCmd") && 
	    		pengine.unify(curT, Term.createTerm("lightCmd(CMD)")) && 
	    		pengine.unify(curT, Term.createTerm( currentEvent.getMsg() ) )){ 
	    			{/* JavaLikeMove */ 
	    			String arg1 = "blink" ;
	    			//end arg1
	    			it.unibo.finalTask2018.ra.hueAdapter.setLight(this,arg1 );
	    			}
	    	}
	    	//onEvent 
	    	setCurrentMsgFromStore(); 
	    	curT = Term.createTerm("lightCmd(on)");
	    	if( currentEvent != null && currentEvent.getEventId().equals("lightCmd") && 
	    		pengine.unify(curT, Term.createTerm("lightCmd(CMD)")) && 
	    		pengine.unify(curT, Term.createTerm( currentEvent.getMsg() ) )){ 
	    			{/* JavaLikeMove */ 
	    			String arg1 = "on" ;
	    			//end arg1
	    			it.unibo.finalTask2018.ra.hueAdapter.setLight(this,arg1 );
	    			}
	    	}
	    	//onEvent 
	    	setCurrentMsgFromStore(); 
	    	curT = Term.createTerm("lightCmd(off)");
	    	if( currentEvent != null && currentEvent.getEventId().equals("lightCmd") && 
	    		pengine.unify(curT, Term.createTerm("lightCmd(CMD)")) && 
	    		pengine.unify(curT, Term.createTerm( currentEvent.getMsg() ) )){ 
	    			{/* JavaLikeMove */ 
	    			String arg1 = "off" ;
	    			//end arg1
	    			it.unibo.finalTask2018.ra.hueAdapter.setLight(this,arg1 );
	    			}
	    	}
	    	repeatPlanNoTransition(pr,myselfName,"huelampagentra_"+myselfName,false,true);
	    }catch(Exception e_commandLamp){  
	    	 println( getName() + " plan=commandLamp WARNING:" + e_commandLamp.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//commandLamp
	    
	    protected void initSensorSystem(){
	    	//doing nothing in a QActor
	    }
	
	}
