/* Generated by AN DISI Unibo */ 
package it.unibo.testapplqa;
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
public abstract class AbstractTestapplqa extends QActor { 
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
		public AbstractTestapplqa(String actorId, QActorContext myCtx, IOutputEnvView outEnvView )  throws Exception{
			super(actorId, myCtx,  
			"./srcMore/it/unibo/testapplqa/WorldTheory.pl",
			setTheEnv( outEnvView )  , "init");
			this.planFilePath = "./srcMore/it/unibo/testapplqa/plans.txt";
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
	    	stateTab.put("waitForCmd",waitForCmd);
	    	stateTab.put("msgReceived",msgReceived);
	    }
	    StateFun handleToutBuiltIn = () -> {	
	    	try{	
	    		PlanRepeat pr = PlanRepeat.setUp("handleTout",-1);
	    		String myselfName = "handleToutBuiltIn";  
	    		println( "testapplqa tout : stops");  
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
	    	temporaryStr = "\"testapplqa start\"";
	    	println( temporaryStr );  
	    	//switchTo waitForCmd
	        switchToPlanAsNextState(pr, myselfName, "testapplqa_"+myselfName, 
	              "waitForCmd",false, false, null); 
	    }catch(Exception e_init){  
	    	 println( getName() + " plan=init WARNING:" + e_init.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//init
	    
	    StateFun waitForCmd = () -> {	
	    try{	
	     PlanRepeat pr = PlanRepeat.setUp(getName()+"_waitForCmd",0);
	     pr.incNumIter(); 	
	    	String myselfName = "waitForCmd";  
	    	//bbb
	     msgTransition( pr,myselfName,"testapplqa_"+myselfName,false,
	          new StateFun[]{stateTab.get("msgReceived") }, 
	          new String[]{"true","M","cmd" },
	          60000, "handleToutBuiltIn" );//msgTransition
	    }catch(Exception e_waitForCmd){  
	    	 println( getName() + " plan=waitForCmd WARNING:" + e_waitForCmd.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//waitForCmd
	    
	    StateFun msgReceived = () -> {	
	    try{	
	     PlanRepeat pr = PlanRepeat.setUp("msgReceived",-1);
	    	String myselfName = "msgReceived";  
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
	    	repeatPlanNoTransition(pr,myselfName,"testapplqa_"+myselfName,false,true);
	    }catch(Exception e_msgReceived){  
	    	 println( getName() + " plan=msgReceived WARNING:" + e_msgReceived.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//msgReceived
	    
	    protected void initSensorSystem(){
	    	//doing nothing in a QActor
	    }
	
	}
