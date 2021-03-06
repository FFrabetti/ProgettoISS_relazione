/* Generated by AN DISI Unibo */ 
package it.unibo.huelampagentgui;
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
public abstract class AbstractHuelampagentgui extends QActor { 
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
		public AbstractHuelampagentgui(String actorId, QActorContext myCtx, IOutputEnvView outEnvView )  throws Exception{
			super(actorId, myCtx,  
			"./srcMore/it/unibo/huelampagentgui/WorldTheory.pl",
			setTheEnv( outEnvView )  , "init");
			this.planFilePath = "./srcMore/it/unibo/huelampagentgui/plans.txt";
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
	    	stateTab.put("waitForCommand",waitForCommand);
	    	stateTab.put("handleCmd",handleCmd);
	    }
	    StateFun handleToutBuiltIn = () -> {	
	    	try{	
	    		PlanRepeat pr = PlanRepeat.setUp("handleTout",-1);
	    		String myselfName = "handleToutBuiltIn";  
	    		println( "huelampagentgui tout : stops");  
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
	    	temporaryStr = "\"HueLampAgentGui(starts)\"";
	    	println( temporaryStr );  
	    	it.unibo.custom.gui.customBlsGui.createCustomLedGui( myself  );
	    	//delay  ( no more reactive within a plan)
	    	aar = delayReactive(1000,"" , "");
	    	if( aar.getInterrupted() ) curPlanInExec   = "init";
	    	if( ! aar.getGoon() ) return ;
	    	it.unibo.custom.gui.customBlsGui.setLed( myself ,"on"  );
	    	//delay  ( no more reactive within a plan)
	    	aar = delayReactive(1000,"" , "");
	    	if( aar.getInterrupted() ) curPlanInExec   = "init";
	    	if( ! aar.getGoon() ) return ;
	    	it.unibo.custom.gui.customBlsGui.setLed( myself ,"off"  );
	    	//switchTo waitForCommand
	        switchToPlanAsNextState(pr, myselfName, "huelampagentgui_"+myselfName, 
	              "waitForCommand",false, false, null); 
	    }catch(Exception e_init){  
	    	 println( getName() + " plan=init WARNING:" + e_init.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//init
	    
	    StateFun waitForCommand = () -> {	
	    try{	
	     PlanRepeat pr = PlanRepeat.setUp(getName()+"_waitForCommand",0);
	     pr.incNumIter(); 	
	    	String myselfName = "waitForCommand";  
	    	//bbb
	     msgTransition( pr,myselfName,"huelampagentgui_"+myselfName,false,
	          new StateFun[]{stateTab.get("handleCmd") }, 
	          new String[]{"true","E","blinkCmd" },
	          6000000, "handleToutBuiltIn" );//msgTransition
	    }catch(Exception e_waitForCommand){  
	    	 println( getName() + " plan=waitForCommand WARNING:" + e_waitForCommand.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//waitForCommand
	    
	    StateFun handleCmd = () -> {	
	    try{	
	     PlanRepeat pr = PlanRepeat.setUp("handleCmd",-1);
	    	String myselfName = "handleCmd";  
	    	printCurrentEvent(false);
	    	//onEvent 
	    	setCurrentMsgFromStore(); 
	    	curT = Term.createTerm("blinkCmd(blink)");
	    	if( currentEvent != null && currentEvent.getEventId().equals("blinkCmd") && 
	    		pengine.unify(curT, Term.createTerm("blinkCmd(STATE)")) && 
	    		pengine.unify(curT, Term.createTerm( currentEvent.getMsg() ) )){ 
	    			{/* JavaLikeMove */ 
	    			String arg1 = "blink" ;
	    			//end arg1
	    			it.unibo.custom.gui.customBlsGui.setLed(this,arg1 );
	    			}
	    	}
	    	//onEvent 
	    	setCurrentMsgFromStore(); 
	    	curT = Term.createTerm("blinkCmd(off)");
	    	if( currentEvent != null && currentEvent.getEventId().equals("blinkCmd") && 
	    		pengine.unify(curT, Term.createTerm("blinkCmd(STATE)")) && 
	    		pengine.unify(curT, Term.createTerm( currentEvent.getMsg() ) )){ 
	    			{/* JavaLikeMove */ 
	    			String arg1 = "off" ;
	    			//end arg1
	    			it.unibo.custom.gui.customBlsGui.setLed(this,arg1 );
	    			}
	    	}
	    	repeatPlanNoTransition(pr,myselfName,"huelampagentgui_"+myselfName,false,true);
	    }catch(Exception e_handleCmd){  
	    	 println( getName() + " plan=handleCmd WARNING:" + e_handleCmd.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//handleCmd
	    
	    protected void initSensorSystem(){
	    	//doing nothing in a QActor
	    }
	
	}
