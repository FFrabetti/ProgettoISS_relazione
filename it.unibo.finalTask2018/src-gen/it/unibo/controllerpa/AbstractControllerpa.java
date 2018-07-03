/* Generated by AN DISI Unibo */ 
package it.unibo.controllerpa;
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
public abstract class AbstractControllerpa extends QActor { 
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
		public AbstractControllerpa(String actorId, QActorContext myCtx, IOutputEnvView outEnvView )  throws Exception{
			super(actorId, myCtx,  
			"./srcMore/it/unibo/controllerpa/WorldTheory.pl",
			setTheEnv( outEnvView )  , "init");
			this.planFilePath = "./srcMore/it/unibo/controllerpa/plans.txt";
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
	    	stateTab.put("waitForInputEvent",waitForInputEvent);
	    	stateTab.put("handleInputEvent",handleInputEvent);
	    	stateTab.put("handleCmd",handleCmd);
	    }
	    StateFun handleToutBuiltIn = () -> {	
	    	try{	
	    		PlanRepeat pr = PlanRepeat.setUp("handleTout",-1);
	    		String myselfName = "handleToutBuiltIn";  
	    		println( "controllerpa tout : stops");  
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
	    	parg = "consult(\"./resourceModel.pl\")";
	    	//QActorUtils.solveGoal(myself,parg,pengine );  //sets currentActionResult		
	    	solveGoal( parg ); //sept2017
	    	temporaryStr = "qacontrol(starts)";
	    	println( temporaryStr );  
	    	//switchTo waitForInputEvent
	        switchToPlanAsNextState(pr, myselfName, "controllerpa_"+myselfName, 
	              "waitForInputEvent",false, false, null); 
	    }catch(Exception e_init){  
	    	 println( getName() + " plan=init WARNING:" + e_init.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//init
	    
	    StateFun waitForInputEvent = () -> {	
	    try{	
	     PlanRepeat pr = PlanRepeat.setUp(getName()+"_waitForInputEvent",0);
	     pr.incNumIter(); 	
	    	String myselfName = "waitForInputEvent";  
	    	if( (guardVars = QActorUtils.evalTheGuard(this, " !?getModelItem(robots,robot,r1,VALUE)" )) != null ){
	    	temporaryStr = "model(r1,VALUE)";
	    	temporaryStr = QActorUtils.substituteVars(guardVars,temporaryStr);
	    	println( temporaryStr );  
	    	}
	    	if( (guardVars = QActorUtils.evalTheGuard(this, " !?getModelItem(sensor,temperature,t1,VALUE)" )) != null ){
	    	temporaryStr = "model(t1,VALUE)";
	    	temporaryStr = QActorUtils.substituteVars(guardVars,temporaryStr);
	    	println( temporaryStr );  
	    	}
	    	if( (guardVars = QActorUtils.evalTheGuard(this, " !?getModelItem(sensor,clock,c1,VALUE)" )) != null ){
	    	temporaryStr = "model(c1,VALUE)";
	    	temporaryStr = QActorUtils.substituteVars(guardVars,temporaryStr);
	    	println( temporaryStr );  
	    	}
	    	temporaryStr = "\"---------------------------------------\"";
	    	println( temporaryStr );  
	    	//bbb
	     msgTransition( pr,myselfName,"controllerpa_"+myselfName,false,
	          new StateFun[]{stateTab.get("handleInputEvent"), stateTab.get("handleCmd") }, 
	          new String[]{"true","E","inputCtrlEvent", "true","M","cmd" },
	          6000000, "handleToutBuiltIn" );//msgTransition
	    }catch(Exception e_waitForInputEvent){  
	    	 println( getName() + " plan=waitForInputEvent WARNING:" + e_waitForInputEvent.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//waitForInputEvent
	    
	    StateFun handleInputEvent = () -> {	
	    try{	
	     PlanRepeat pr = PlanRepeat.setUp("handleInputEvent",-1);
	    	String myselfName = "handleInputEvent";  
	    	printCurrentEvent(false);
	    	//onEvent 
	    	setCurrentMsgFromStore(); 
	    	curT = Term.createTerm("inputEvent(CATEG,NAME,VALUE)");
	    	if( currentEvent != null && currentEvent.getEventId().equals("inputCtrlEvent") && 
	    		pengine.unify(curT, Term.createTerm("inputEvent(CATEG,NAME,VALUE)")) && 
	    		pengine.unify(curT, Term.createTerm( currentEvent.getMsg() ) )){ 
	    			String parg="changeModelItem(CATEG,NAME,VALUE)";
	    			/* PHead */
	    			parg =  updateVars( Term.createTerm("inputEvent(CATEG,NAME,VALUE)"), 
	    			                    Term.createTerm("inputEvent(CATEG,NAME,VALUE)"), 
	    				    		  	Term.createTerm(currentEvent.getMsg()), parg);
	    				if( parg != null ) {
	    				    aar = QActorUtils.solveGoal(this,myCtx,pengine,parg,"",outEnvView,86400000);
	    					//println(getName() + " plan " + curPlanInExec  +  " interrupted=" + aar.getInterrupted() + " action goon="+aar.getGoon());
	    					if( aar.getInterrupted() ){
	    						curPlanInExec   = "handleInputEvent";
	    						if( aar.getTimeRemained() <= 0 ) addRule("tout(demo,"+getName()+")");
	    						if( ! aar.getGoon() ) return ;
	    					} 			
	    					if( aar.getResult().equals("failure")){
	    						if( ! aar.getGoon() ) return ;
	    					}else if( ! aar.getGoon() ) return ;
	    				}
	    	}
	    	repeatPlanNoTransition(pr,myselfName,"controllerpa_"+myselfName,false,true);
	    }catch(Exception e_handleInputEvent){  
	    	 println( getName() + " plan=handleInputEvent WARNING:" + e_handleInputEvent.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//handleInputEvent
	    
	    StateFun handleCmd = () -> {	
	    try{	
	     PlanRepeat pr = PlanRepeat.setUp("handleCmd",-1);
	    	String myselfName = "handleCmd";  
	    	//onMsg 
	    	setCurrentMsgFromStore(); 
	    	curT = Term.createTerm("cmd(X)");
	    	if( currentMessage != null && currentMessage.msgId().equals("cmd") && 
	    		pengine.unify(curT, Term.createTerm("cmd(X)")) && 
	    		pengine.unify(curT, Term.createTerm( currentMessage.msgContent() ) )){ 
	    		String parg = "ricevuto(X)";
	    		/* Print */
	    		parg =  updateVars( Term.createTerm("cmd(X)"), 
	    		                    Term.createTerm("cmd(X)"), 
	    			    		  	Term.createTerm(currentMessage.msgContent()), parg);
	    		if( parg != null ) println( parg );
	    	}
	    	//onMsg 
	    	setCurrentMsgFromStore(); 
	    	curT = Term.createTerm("cmd(X)");
	    	if( currentMessage != null && currentMessage.msgId().equals("cmd") && 
	    		pengine.unify(curT, Term.createTerm("cmd(X)")) && 
	    		pengine.unify(curT, Term.createTerm( currentMessage.msgContent() ) )){ 
	    		String parg="changeRobotModel(X)";
	    		/* PHead */
	    		parg =  updateVars( Term.createTerm("cmd(X)"), 
	    		                    Term.createTerm("cmd(X)"), 
	    			    		  	Term.createTerm(currentMessage.msgContent()), parg);
	    			if( parg != null ) {
	    			    aar = QActorUtils.solveGoal(this,myCtx,pengine,parg,"",outEnvView,86400000);
	    				//println(getName() + " plan " + curPlanInExec  +  " interrupted=" + aar.getInterrupted() + " action goon="+aar.getGoon());
	    				if( aar.getInterrupted() ){
	    					curPlanInExec   = "handleCmd";
	    					if( aar.getTimeRemained() <= 0 ) addRule("tout(demo,"+getName()+")");
	    					if( ! aar.getGoon() ) return ;
	    				} 			
	    				if( aar.getResult().equals("failure")){
	    					if( ! aar.getGoon() ) return ;
	    				}else if( ! aar.getGoon() ) return ;
	    			}
	    	}
	    	repeatPlanNoTransition(pr,myselfName,"controllerpa_"+myselfName,false,true);
	    }catch(Exception e_handleCmd){  
	    	 println( getName() + " plan=handleCmd WARNING:" + e_handleCmd.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//handleCmd
	    
	    protected void initSensorSystem(){
	    	//doing nothing in a QActor
	    }
	
	}
