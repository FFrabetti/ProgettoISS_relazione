/* Generated by AN DISI Unibo */ 
package it.unibo.swag2pa;
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
public abstract class AbstractSwag2pa extends QActor { 
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
		public AbstractSwag2pa(String actorId, QActorContext myCtx, IOutputEnvView outEnvView )  throws Exception{
			super(actorId, myCtx,  
			"./srcMore/it/unibo/swag2pa/WorldTheory.pl",
			setTheEnv( outEnvView )  , "init");
			this.planFilePath = "./srcMore/it/unibo/swag2pa/plans.txt";
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
	    	stateTab.put("receivedCmd",receivedCmd);
	    	stateTab.put("notInStartingPoint",notInStartingPoint);
	    	stateTab.put("detectedBySonar",detectedBySonar);
	    	stateTab.put("cleaning",cleaning);
	    	stateTab.put("detectedByFinal",detectedByFinal);
	    	stateTab.put("handleFront",handleFront);
	    	stateTab.put("avoidFix",avoidFix);
	    	stateTab.put("givingUp",givingUp);
	    	stateTab.put("avoidMobile",avoidMobile);
	    	stateTab.put("checkDoor",checkDoor);
	    	stateTab.put("doorFound",doorFound);
	    	stateTab.put("goToPrevLevel",goToPrevLevel);
	    	stateTab.put("failure",failure);
	    }
	    StateFun handleToutBuiltIn = () -> {	
	    	try{	
	    		PlanRepeat pr = PlanRepeat.setUp("handleTout",-1);
	    		String myselfName = "handleToutBuiltIn";  
	    		println( "swag2pa tout : stops");  
	    		repeatPlanNoTransition(pr,myselfName,"application_"+myselfName,false,false);
	    	}catch(Exception e_handleToutBuiltIn){  
	    		println( getName() + " plan=handleToutBuiltIn WARNING:" + e_handleToutBuiltIn.getMessage() );
	    		QActorContext.terminateQActorSystem(this); 
	    	}
	    };//handleToutBuiltIn
	    
	    StateFun init = () -> {	
	    try{	
	     PlanRepeat pr = PlanRepeat.setUp(getName()+"_init",0);
	     pr.incNumIter(); 	
	    	String myselfName = "init";  
	    	temporaryStr = "\"swag2 start: waiting for start command\"";
	    	println( temporaryStr );  
	    	//bbb
	     msgTransition( pr,myselfName,"swag2pa_"+myselfName,false,
	          new StateFun[]{stateTab.get("receivedCmd") }, 
	          new String[]{"true","M","swagmsg" },
	          3600000, "handleToutBuiltIn" );//msgTransition
	    }catch(Exception e_init){  
	    	 println( getName() + " plan=init WARNING:" + e_init.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//init
	    
	    StateFun receivedCmd = () -> {	
	    try{	
	     PlanRepeat pr = PlanRepeat.setUp("receivedCmd",-1);
	    	String myselfName = "receivedCmd";  
	    	//onMsg 
	    	setCurrentMsgFromStore(); 
	    	curT = Term.createTerm("usercmd(start)");
	    	if( currentMessage != null && currentMessage.msgId().equals("swagmsg") && 
	    		pengine.unify(curT, Term.createTerm("usercmd(CMD)")) && 
	    		pengine.unify(curT, Term.createTerm( currentMessage.msgContent() ) )){ 
	    		//println("WARNING: variable substitution not yet fully implemented " ); 
	    		{//actionseq
	    		temporaryStr = "\"ricevuto usercmd(start)\"";
	    		println( temporaryStr );  
	    		temporaryStr = "startCmd";
	    		addRule( temporaryStr );  
	    		};//actionseq
	    	}
	    	//onMsg 
	    	setCurrentMsgFromStore(); 
	    	curT = Term.createTerm("usercmd(halt)");
	    	if( currentMessage != null && currentMessage.msgId().equals("swagmsg") && 
	    		pengine.unify(curT, Term.createTerm("usercmd(CMD)")) && 
	    		pengine.unify(curT, Term.createTerm( currentMessage.msgContent() ) )){ 
	    		String parg = "\"ricevuto usercmd(halt)\"";
	    		/* Print */
	    		parg =  updateVars( Term.createTerm("usercmd(CMD)"), 
	    		                    Term.createTerm("usercmd(halt)"), 
	    			    		  	Term.createTerm(currentMessage.msgContent()), parg);
	    		if( parg != null ) println( parg );
	    	}
	    	//bbb
	     msgTransition( pr,myselfName,"swag2pa_"+myselfName,false,
	          new StateFun[]{stateTab.get("detectedBySonar") }, 
	          new String[]{" ??startCmd" ,"E","sonarSensor" },
	          800, "notInStartingPoint" );//msgTransition
	    }catch(Exception e_receivedCmd){  
	    	 println( getName() + " plan=receivedCmd WARNING:" + e_receivedCmd.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//receivedCmd
	    
	    StateFun notInStartingPoint = () -> {	
	    try{	
	     PlanRepeat pr = PlanRepeat.setUp("notInStartingPoint",-1);
	    	String myselfName = "notInStartingPoint";  
	    	temporaryStr = "\"not in starting point\"";
	    	println( temporaryStr );  
	    	temporaryStr = "startCmd";
	    	removeRule( temporaryStr );  
	    	//switchTo init
	        switchToPlanAsNextState(pr, myselfName, "swag2pa_"+myselfName, 
	              "init",false, false, null); 
	    }catch(Exception e_notInStartingPoint){  
	    	 println( getName() + " plan=notInStartingPoint WARNING:" + e_notInStartingPoint.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//notInStartingPoint
	    
	    StateFun detectedBySonar = () -> {	
	    try{	
	     PlanRepeat pr = PlanRepeat.setUp("detectedBySonar",-1);
	    	String myselfName = "detectedBySonar";  
	    	temporaryStr = "\"detected by a sonar\"";
	    	println( temporaryStr );  
	    	//onEvent 
	    	setCurrentMsgFromStore(); 
	    	curT = Term.createTerm("sonar(sonar1,D)");
	    	if( currentEvent != null && currentEvent.getEventId().equals("sonarSensor") && 
	    		pengine.unify(curT, Term.createTerm("sonar(NAME,DISTANCE)")) && 
	    		pengine.unify(curT, Term.createTerm( currentEvent.getMsg() ) )){ 
	    			String parg="sonarDetect(sonar1,D)";
	    			/* AddRule */
	    			parg = updateVars(Term.createTerm("sonar(NAME,DISTANCE)"),  Term.createTerm("sonar(sonar1,D)"), 
	    				    		  					Term.createTerm(currentEvent.getMsg()), parg);
	    			if( parg != null ) addRule(parg);	    		  					
	    	}
	    	if( (guardVars = QActorUtils.evalTheGuard(this, " !?isCloseTo(sonar1)" )) != null ){
	    	{//actionseq
	    	temporaryStr = "\"close to sonar1\"";
	    	println( temporaryStr );  
	    	temporaryStr = QActorUtils.unifyMsgContent(pengine,"usercmd(CMD)","usercmd(clean)", guardVars ).toString();
	    	sendMsg("swagmsg",getNameNoCtrl(), QActorContext.dispatch, temporaryStr ); 
	    	};//actionseq
	    	}
	    	else{ temporaryStr = "\"NOT close to sonar1!\"";
	    	println( temporaryStr );  
	    	}temporaryStr = "sonarDetect(sonar1,D)";
	    	removeRule( temporaryStr );  
	    	//bbb
	     msgTransition( pr,myselfName,"swag2pa_"+myselfName,false,
	          new StateFun[]{stateTab.get("cleaning") }, 
	          new String[]{"true","M","swagmsg" },
	          800, "notInStartingPoint" );//msgTransition
	    }catch(Exception e_detectedBySonar){  
	    	 println( getName() + " plan=detectedBySonar WARNING:" + e_detectedBySonar.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//detectedBySonar
	    
	    StateFun cleaning = () -> {	
	    try{	
	     PlanRepeat pr = PlanRepeat.setUp(getName()+"_cleaning",0);
	     pr.incNumIter(); 	
	    	String myselfName = "cleaning";  
	    	temporaryStr = "\"cleaning\"";
	    	println( temporaryStr );  
	    	//bbb
	     msgTransition( pr,myselfName,"swag2pa_"+myselfName,false,
	          new StateFun[]{stateTab.get("handleFront"), stateTab.get("detectedByFinal"), stateTab.get("receivedCmd") }, 
	          new String[]{"true","E","frontSonar", "true","E","sonarSensor", "true","M","swagmsg" },
	          3600000, "handleToutBuiltIn" );//msgTransition
	    }catch(Exception e_cleaning){  
	    	 println( getName() + " plan=cleaning WARNING:" + e_cleaning.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//cleaning
	    
	    StateFun detectedByFinal = () -> {	
	    try{	
	     PlanRepeat pr = PlanRepeat.setUp("detectedByFinal",-1);
	    	String myselfName = "detectedByFinal";  
	    	temporaryStr = "\"detected by a sonar\"";
	    	println( temporaryStr );  
	    	//onEvent 
	    	setCurrentMsgFromStore(); 
	    	curT = Term.createTerm("sonar(sonar2,D)");
	    	if( currentEvent != null && currentEvent.getEventId().equals("sonarSensor") && 
	    		pengine.unify(curT, Term.createTerm("sonar(NAME,DISTANCE)")) && 
	    		pengine.unify(curT, Term.createTerm( currentEvent.getMsg() ) )){ 
	    			String parg="sonarDetect(sonar2,D)";
	    			/* AddRule */
	    			parg = updateVars(Term.createTerm("sonar(NAME,DISTANCE)"),  Term.createTerm("sonar(sonar2,D)"), 
	    				    		  					Term.createTerm(currentEvent.getMsg()), parg);
	    			if( parg != null ) addRule(parg);	    		  					
	    	}
	    	if( (guardVars = QActorUtils.evalTheGuard(this, " !?isCloseTo(sonar2)" )) != null ){
	    	{//actionseq
	    	temporaryStr = "\"close to sonar2\"";
	    	println( temporaryStr );  
	    	temporaryStr = QActorUtils.unifyMsgContent(pengine,"usercmd(CMD)","usercmd(halt)", guardVars ).toString();
	    	sendMsg("swagmsg",getNameNoCtrl(), QActorContext.dispatch, temporaryStr ); 
	    	};//actionseq
	    	}
	    	else{ temporaryStr = "\"NOT close to sonar2!\"";
	    	println( temporaryStr );  
	    	}temporaryStr = "sonarDetect(sonar2,D)";
	    	removeRule( temporaryStr );  
	    	//bbb
	     msgTransition( pr,myselfName,"swag2pa_"+myselfName,false,
	          new StateFun[]{stateTab.get("init") }, 
	          new String[]{"true","M","swagmsg" },
	          800, "cleaning" );//msgTransition
	    }catch(Exception e_detectedByFinal){  
	    	 println( getName() + " plan=detectedByFinal WARNING:" + e_detectedByFinal.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//detectedByFinal
	    
	    StateFun handleFront = () -> {	
	    try{	
	     PlanRepeat pr = PlanRepeat.setUp("handleFront",-1);
	    	String myselfName = "handleFront";  
	    	temporaryStr = "\"handleFront\"";
	    	println( temporaryStr );  
	    	printCurrentEvent(false);
	    	//onEvent 
	    	setCurrentMsgFromStore(); 
	    	curT = Term.createTerm("sonar(D)");
	    	if( currentEvent != null && currentEvent.getEventId().equals("frontSonar") && 
	    		pengine.unify(curT, Term.createTerm("sonar(DISTANCE)")) && 
	    		pengine.unify(curT, Term.createTerm( currentEvent.getMsg() ) )){ 
	    			String parg="obstacleDetected(D)";
	    			/* PHead */
	    			parg =  updateVars( Term.createTerm("sonar(DISTANCE)"), 
	    			                    Term.createTerm("sonar(D)"), 
	    				    		  	Term.createTerm(currentEvent.getMsg()), parg);
	    				if( parg != null ) {
	    				    aar = QActorUtils.solveGoal(this,myCtx,pengine,parg,"",outEnvView,86400000);
	    					//println(getName() + " plan " + curPlanInExec  +  " interrupted=" + aar.getInterrupted() + " action goon="+aar.getGoon());
	    					if( aar.getInterrupted() ){
	    						curPlanInExec   = "handleFront";
	    						if( aar.getTimeRemained() <= 0 ) addRule("tout(demo,"+getName()+")");
	    						if( ! aar.getGoon() ) return ;
	    					} 			
	    					if( aar.getResult().equals("failure")){
	    						if( ! aar.getGoon() ) return ;
	    					}else if( ! aar.getGoon() ) return ;
	    				}
	    	}
	    	if( (guardVars = QActorUtils.evalTheGuard(this, " !?isFixObstacle" )) != null ){
	    	temporaryStr = "\"fix obstacle\"";
	    	temporaryStr = QActorUtils.substituteVars(guardVars,temporaryStr);
	    	println( temporaryStr );  
	    	}
	    	else{ {//actionseq
	    	temporaryStr = "\"possible mobile obstacle\"";
	    	println( temporaryStr );  
	    	//delay  ( no more reactive within a plan)
	    	aar = delayReactive(1000,"" , "");
	    	if( aar.getInterrupted() ) curPlanInExec   = "handleFront";
	    	if( ! aar.getGoon() ) return ;
	    	temporaryStr = "\"DELAY\"";
	    	println( temporaryStr );  
	    	};//actionseq
	    	}
	    	//bbb
	     msgTransition( pr,myselfName,"swag2pa_"+myselfName,true,
	          new StateFun[]{stateTab.get("avoidFix") }, 
	          new String[]{"true","E","frontSonar" },
	          800, "avoidMobile" );//msgTransition
	    }catch(Exception e_handleFront){  
	    	 println( getName() + " plan=handleFront WARNING:" + e_handleFront.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//handleFront
	    
	    StateFun avoidFix = () -> {	
	    try{	
	     PlanRepeat pr = PlanRepeat.setUp("avoidFix",-1);
	    	String myselfName = "avoidFix";  
	    	temporaryStr = "\"avoidFix\"";
	    	println( temporaryStr );  
	    	temporaryStr = "foundObstacle(X)";
	    	removeRule( temporaryStr );  
	    	parg = "avoidFixTry";
	    	//QActorUtils.solveGoal(myself,parg,pengine );  //sets currentActionResult		
	    	solveGoal( parg ); //sept2017
	    	if( (guardVars = QActorUtils.evalTheGuard(this, " !?avoidFixGiveUp" )) != null ){
	    	temporaryStr = QActorUtils.unifyMsgContent(pengine,"usercmd(CMD)","usercmd(giveup)", guardVars ).toString();
	    	sendMsg("swagmsg",getNameNoCtrl(), QActorContext.dispatch, temporaryStr ); 
	    	}
	    	else{ {//actionseq
	    	temporaryStr = "\"proviamo a girarci intorno\"";
	    	println( temporaryStr );  
	    	temporaryStr = "\"turn right\"";
	    	println( temporaryStr );  
	    	temporaryStr = "\"forward\"";
	    	println( temporaryStr );  
	    	};//actionseq
	    	}
	    	//bbb
	     msgTransition( pr,myselfName,"swag2pa_"+myselfName,false,
	          new StateFun[]{stateTab.get("failure"), stateTab.get("givingUp") }, 
	          new String[]{"true","E","frontSonar", "true","M","swagmsg" },
	          800, "checkDoor" );//msgTransition
	    }catch(Exception e_avoidFix){  
	    	 println( getName() + " plan=avoidFix WARNING:" + e_avoidFix.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//avoidFix
	    
	    StateFun givingUp = () -> {	
	    try{	
	     PlanRepeat pr = PlanRepeat.setUp("givingUp",-1);
	    	String myselfName = "givingUp";  
	    	temporaryStr = "\"givingUp\"";
	    	println( temporaryStr );  
	    	temporaryStr = "foundFix(X)";
	    	removeRule( temporaryStr );  
	    	//switchTo init
	        switchToPlanAsNextState(pr, myselfName, "swag2pa_"+myselfName, 
	              "init",false, false, null); 
	    }catch(Exception e_givingUp){  
	    	 println( getName() + " plan=givingUp WARNING:" + e_givingUp.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//givingUp
	    
	    StateFun avoidMobile = () -> {	
	    try{	
	     PlanRepeat pr = PlanRepeat.setUp("avoidMobile",-1);
	    	String myselfName = "avoidMobile";  
	    	temporaryStr = "\"avoidMobile\"";
	    	println( temporaryStr );  
	    	temporaryStr = "foundObstacle(X)";
	    	removeRule( temporaryStr );  
	    	temporaryStr = "\"ok, ostacolo superato\"";
	    	println( temporaryStr );  
	    	//switchTo cleaning
	        switchToPlanAsNextState(pr, myselfName, "swag2pa_"+myselfName, 
	              "cleaning",false, false, null); 
	    }catch(Exception e_avoidMobile){  
	    	 println( getName() + " plan=avoidMobile WARNING:" + e_avoidMobile.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//avoidMobile
	    
	    StateFun checkDoor = () -> {	
	    try{	
	     PlanRepeat pr = PlanRepeat.setUp("checkDoor",-1);
	    	String myselfName = "checkDoor";  
	    	temporaryStr = "\"checkDoor\"";
	    	println( temporaryStr );  
	    	temporaryStr = "\"stop\"";
	    	println( temporaryStr );  
	    	temporaryStr = "\"turn left\"";
	    	println( temporaryStr );  
	    	//bbb
	     msgTransition( pr,myselfName,"swag2pa_"+myselfName,false,
	          new StateFun[]{stateTab.get("avoidFix") }, 
	          new String[]{"true","E","frontSonar" },
	          8000, "doorFound" );//msgTransition
	    }catch(Exception e_checkDoor){  
	    	 println( getName() + " plan=checkDoor WARNING:" + e_checkDoor.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//checkDoor
	    
	    StateFun doorFound = () -> {	
	    try{	
	     PlanRepeat pr = PlanRepeat.setUp("doorFound",-1);
	    	String myselfName = "doorFound";  
	    	temporaryStr = "\"doorFound\"";
	    	println( temporaryStr );  
	    	temporaryStr = "foundFix(X)";
	    	removeRule( temporaryStr );  
	    	temporaryStr = "\"forward\"";
	    	println( temporaryStr );  
	    	//delay  ( no more reactive within a plan)
	    	aar = delayReactive(400,"" , "");
	    	if( aar.getInterrupted() ) curPlanInExec   = "doorFound";
	    	if( ! aar.getGoon() ) return ;
	    	temporaryStr = "\"stop\"";
	    	println( temporaryStr );  
	    	//switchTo goToPrevLevel
	        switchToPlanAsNextState(pr, myselfName, "swag2pa_"+myselfName, 
	              "goToPrevLevel",false, false, null); 
	    }catch(Exception e_doorFound){  
	    	 println( getName() + " plan=doorFound WARNING:" + e_doorFound.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//doorFound
	    
	    StateFun goToPrevLevel = () -> {	
	    try{	
	     PlanRepeat pr = PlanRepeat.setUp("goToPrevLevel",-1);
	    	String myselfName = "goToPrevLevel";  
	    	temporaryStr = "\"goToPrevLevel\"";
	    	println( temporaryStr );  
	    	repeatPlanNoTransition(pr,myselfName,"swag2pa_"+myselfName,false,false);
	    }catch(Exception e_goToPrevLevel){  
	    	 println( getName() + " plan=goToPrevLevel WARNING:" + e_goToPrevLevel.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//goToPrevLevel
	    
	    StateFun failure = () -> {	
	    try{	
	     PlanRepeat pr = PlanRepeat.setUp("failure",-1);
	    	String myselfName = "failure";  
	    	temporaryStr = "\"failure\"";
	    	println( temporaryStr );  
	    	temporaryStr = "foundFix(X)";
	    	removeRule( temporaryStr );  
	    	temporaryStr = "\"stop\"";
	    	println( temporaryStr );  
	    	temporaryStr = "\"prova a sinistra\"";
	    	println( temporaryStr );  
	    	repeatPlanNoTransition(pr,myselfName,"swag2pa_"+myselfName,false,false);
	    }catch(Exception e_failure){  
	    	 println( getName() + " plan=failure WARNING:" + e_failure.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//failure
	    
	    protected void initSensorSystem(){
	    	//doing nothing in a QActor
	    }
	
	}
