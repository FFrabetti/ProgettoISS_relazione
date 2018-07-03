/* Generated by AN DISI Unibo */ 
package it.unibo.swagpa;
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
public abstract class AbstractSwagpa extends QActor { 
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
		public AbstractSwagpa(String actorId, QActorContext myCtx, IOutputEnvView outEnvView )  throws Exception{
			super(actorId, myCtx,  
			"./srcMore/it/unibo/swagpa/WorldTheory.pl",
			setTheEnv( outEnvView )  , "init");
			this.planFilePath = "./srcMore/it/unibo/swagpa/plans.txt";
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
	    	stateTab.put("waitForSonar",waitForSonar);
	    	stateTab.put("handleFront",handleFront);
	    	stateTab.put("handleSensor",handleSensor);
	    }
	    StateFun handleToutBuiltIn = () -> {	
	    	try{	
	    		PlanRepeat pr = PlanRepeat.setUp("handleTout",-1);
	    		String myselfName = "handleToutBuiltIn";  
	    		println( "swagpa tout : stops");  
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
	    	temporaryStr = "\"swag start\"";
	    	println( temporaryStr );  
	    	//switchTo waitForSonar
	        switchToPlanAsNextState(pr, myselfName, "swagpa_"+myselfName, 
	              "waitForSonar",false, false, null); 
	    }catch(Exception e_init){  
	    	 println( getName() + " plan=init WARNING:" + e_init.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//init
	    
	    StateFun waitForSonar = () -> {	
	    try{	
	     PlanRepeat pr = PlanRepeat.setUp(getName()+"_waitForSonar",0);
	     pr.incNumIter(); 	
	    	String myselfName = "waitForSonar";  
	    	//bbb
	     msgTransition( pr,myselfName,"swagpa_"+myselfName,false,
	          new StateFun[]{stateTab.get("handleFront"), stateTab.get("handleSensor") }, 
	          new String[]{"true","E","frontSonar", "true","E","sonarSensor" },
	          3600000, "handleToutBuiltIn" );//msgTransition
	    }catch(Exception e_waitForSonar){  
	    	 println( getName() + " plan=waitForSonar WARNING:" + e_waitForSonar.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//waitForSonar
	    
	    StateFun handleFront = () -> {	
	    try{	
	     PlanRepeat pr = PlanRepeat.setUp("handleFront",-1);
	    	String myselfName = "handleFront";  
	    	printCurrentEvent(false);
	    	//onEvent 
	    	setCurrentMsgFromStore(); 
	    	curT = Term.createTerm("sonar(D)");
	    	if( currentEvent != null && currentEvent.getEventId().equals("frontSonar") && 
	    		pengine.unify(curT, Term.createTerm("sonar(DISTANCE)")) && 
	    		pengine.unify(curT, Term.createTerm( currentEvent.getMsg() ) )){ 
	    			String parg="cmd(h(0))";
	    			/* SendDispatch */
	    			parg = updateVars(Term.createTerm("sonar(DISTANCE)"),  Term.createTerm("sonar(D)"), 
	    				    		  					Term.createTerm(currentEvent.getMsg()), parg);
	    			if( parg != null ) sendMsg("cmd","controllerpa", QActorContext.dispatch, parg ); 
	    	}
	    	repeatPlanNoTransition(pr,myselfName,"swagpa_"+myselfName,false,true);
	    }catch(Exception e_handleFront){  
	    	 println( getName() + " plan=handleFront WARNING:" + e_handleFront.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//handleFront
	    
	    StateFun handleSensor = () -> {	
	    try{	
	     PlanRepeat pr = PlanRepeat.setUp("handleSensor",-1);
	    	String myselfName = "handleSensor";  
	    	printCurrentEvent(false);
	    	//onEvent 
	    	setCurrentMsgFromStore(); 
	    	curT = Term.createTerm("sonar(N,D)");
	    	if( currentEvent != null && currentEvent.getEventId().equals("sonarSensor") && 
	    		pengine.unify(curT, Term.createTerm("sonar(NAME,DISTANCE)")) && 
	    		pengine.unify(curT, Term.createTerm( currentEvent.getMsg() ) )){ 
	    			String parg="sonarDetect(N,D)";
	    			/* AddRule */
	    			parg = updateVars(Term.createTerm("sonar(NAME,DISTANCE)"),  Term.createTerm("sonar(N,D)"), 
	    				    		  					Term.createTerm(currentEvent.getMsg()), parg);
	    			if( parg != null ) addRule(parg);	    		  					
	    	}
	    	if( (guardVars = QActorUtils.evalTheGuard(this, " !?isClose" )) != null ){
	    	{//actionseq
	    	temporaryStr = QActorUtils.unifyMsgContent(pengine,"cmd(X)","cmd(d(0))", guardVars ).toString();
	    	sendMsg("cmd","controllerpa", QActorContext.dispatch, temporaryStr ); 
	    	//delay  ( no more reactive within a plan)
	    	aar = delayReactive(250,"" , "");
	    	if( aar.getInterrupted() ) curPlanInExec   = "handleSensor";
	    	if( ! aar.getGoon() ) return ;
	    	temporaryStr = QActorUtils.unifyMsgContent(pengine,"cmd(X)","cmd(d(0))", guardVars ).toString();
	    	sendMsg("cmd","controllerpa", QActorContext.dispatch, temporaryStr ); 
	    	//delay  ( no more reactive within a plan)
	    	aar = delayReactive(250,"" , "");
	    	if( aar.getInterrupted() ) curPlanInExec   = "handleSensor";
	    	if( ! aar.getGoon() ) return ;
	    	temporaryStr = QActorUtils.unifyMsgContent(pengine,"cmd(X)","cmd(d(0))", guardVars ).toString();
	    	sendMsg("cmd","controllerpa", QActorContext.dispatch, temporaryStr ); 
	    	//delay  ( no more reactive within a plan)
	    	aar = delayReactive(250,"" , "");
	    	if( aar.getInterrupted() ) curPlanInExec   = "handleSensor";
	    	if( ! aar.getGoon() ) return ;
	    	temporaryStr = QActorUtils.unifyMsgContent(pengine,"cmd(X)","cmd(d(0))", guardVars ).toString();
	    	sendMsg("cmd","controllerpa", QActorContext.dispatch, temporaryStr ); 
	    	//delay  ( no more reactive within a plan)
	    	aar = delayReactive(250,"" , "");
	    	if( aar.getInterrupted() ) curPlanInExec   = "handleSensor";
	    	if( ! aar.getGoon() ) return ;
	    	temporaryStr = QActorUtils.unifyMsgContent(pengine,"cmd(X)","cmd(s(0))", guardVars ).toString();
	    	sendMsg("cmd","controllerpa", QActorContext.dispatch, temporaryStr ); 
	    	//delay  ( no more reactive within a plan)
	    	aar = delayReactive(250,"" , "");
	    	if( aar.getInterrupted() ) curPlanInExec   = "handleSensor";
	    	if( ! aar.getGoon() ) return ;
	    	temporaryStr = QActorUtils.unifyMsgContent(pengine,"cmd(X)","cmd(h(0))", guardVars ).toString();
	    	sendMsg("cmd","controllerpa", QActorContext.dispatch, temporaryStr ); 
	    	};//actionseq
	    	}
	    	if( (guardVars = QActorUtils.evalTheGuard(this, " ??sonarDetect(N,D)" )) != null ){
	    	temporaryStr = "removing(N,D)";
	    	temporaryStr = QActorUtils.substituteVars(guardVars,temporaryStr);
	    	println( temporaryStr );  
	    	}
	    	repeatPlanNoTransition(pr,myselfName,"swagpa_"+myselfName,false,true);
	    }catch(Exception e_handleSensor){  
	    	 println( getName() + " plan=handleSensor WARNING:" + e_handleSensor.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//handleSensor
	    
	    protected void initSensorSystem(){
	    	//doing nothing in a QActor
	    }
	
	}
