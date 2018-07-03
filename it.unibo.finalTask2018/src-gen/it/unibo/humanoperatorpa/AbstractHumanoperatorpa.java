/* Generated by AN DISI Unibo */ 
package it.unibo.humanoperatorpa;
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
public abstract class AbstractHumanoperatorpa extends QActor { 
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
		public AbstractHumanoperatorpa(String actorId, QActorContext myCtx, IOutputEnvView outEnvView )  throws Exception{
			super(actorId, myCtx,  
			"./srcMore/it/unibo/humanoperatorpa/WorldTheory.pl",
			setTheEnv( outEnvView )  , "init");
			this.planFilePath = "./srcMore/it/unibo/humanoperatorpa/plans.txt";
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
	    	stateTab.put("waitForUserCmd",waitForUserCmd);
	    	stateTab.put("sendCmd",sendCmd);
	    }
	    StateFun handleToutBuiltIn = () -> {	
	    	try{	
	    		PlanRepeat pr = PlanRepeat.setUp("handleTout",-1);
	    		String myselfName = "handleToutBuiltIn";  
	    		println( "humanoperatorpa tout : stops");  
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
	    	temporaryStr = "\"humanOperator start\"";
	    	println( temporaryStr );  
	    	//switchTo waitForUserCmd
	        switchToPlanAsNextState(pr, myselfName, "humanoperatorpa_"+myselfName, 
	              "waitForUserCmd",false, false, null); 
	    }catch(Exception e_init){  
	    	 println( getName() + " plan=init WARNING:" + e_init.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//init
	    
	    StateFun waitForUserCmd = () -> {	
	    try{	
	     PlanRepeat pr = PlanRepeat.setUp(getName()+"_waitForUserCmd",0);
	     pr.incNumIter(); 	
	    	String myselfName = "waitForUserCmd";  
	    	//bbb
	     msgTransition( pr,myselfName,"humanoperatorpa_"+myselfName,false,
	          new StateFun[]{stateTab.get("sendCmd") }, 
	          new String[]{"true","E","usercmd" },
	          3600000, "handleToutBuiltIn" );//msgTransition
	    }catch(Exception e_waitForUserCmd){  
	    	 println( getName() + " plan=waitForUserCmd WARNING:" + e_waitForUserCmd.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//waitForUserCmd
	    
	    StateFun sendCmd = () -> {	
	    try{	
	     PlanRepeat pr = PlanRepeat.setUp("sendCmd",-1);
	    	String myselfName = "sendCmd";  
	    	//onEvent 
	    	setCurrentMsgFromStore(); 
	    	curT = Term.createTerm("usercmd(robotgui(CMD))");
	    	if( currentEvent != null && currentEvent.getEventId().equals("usercmd") && 
	    		pengine.unify(curT, Term.createTerm("usercmd(X)")) && 
	    		pengine.unify(curT, Term.createTerm( currentEvent.getMsg() ) )){ 
	    			String parg = "robotgui(CMD)";
	    			/* Print */
	    			parg =  updateVars( Term.createTerm("usercmd(X)"), 
	    			                    Term.createTerm("usercmd(robotgui(CMD))"), 
	    				    		  	Term.createTerm(currentEvent.getMsg()), parg);
	    			if( parg != null ) println( parg );
	    	}
	    	//onEvent 
	    	setCurrentMsgFromStore(); 
	    	curT = Term.createTerm("usercmd(robotgui(CMD))");
	    	if( currentEvent != null && currentEvent.getEventId().equals("usercmd") && 
	    		pengine.unify(curT, Term.createTerm("usercmd(X)")) && 
	    		pengine.unify(curT, Term.createTerm( currentEvent.getMsg() ) )){ 
	    			String parg="cmd(CMD)";
	    			/* SendDispatch */
	    			parg = updateVars(Term.createTerm("usercmd(X)"),  Term.createTerm("usercmd(robotgui(CMD))"), 
	    				    		  					Term.createTerm(currentEvent.getMsg()), parg);
	    			if( parg != null ) sendMsg("cmd","controllerpa", QActorContext.dispatch, parg ); 
	    	}
	    	repeatPlanNoTransition(pr,myselfName,"humanoperatorpa_"+myselfName,false,true);
	    }catch(Exception e_sendCmd){  
	    	 println( getName() + " plan=sendCmd WARNING:" + e_sendCmd.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//sendCmd
	    
	    protected void initSensorSystem(){
	    	//doing nothing in a QActor
	    }
	
	}
