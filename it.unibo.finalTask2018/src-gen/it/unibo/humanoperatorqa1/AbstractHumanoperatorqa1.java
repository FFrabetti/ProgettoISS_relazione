/* Generated by AN DISI Unibo */ 
package it.unibo.humanoperatorqa1;
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
public abstract class AbstractHumanoperatorqa1 extends QActor { 
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
		public AbstractHumanoperatorqa1(String actorId, QActorContext myCtx, IOutputEnvView outEnvView )  throws Exception{
			super(actorId, myCtx,  
			"./srcMore/it/unibo/humanoperatorqa1/WorldTheory.pl",
			setTheEnv( outEnvView )  , "init");
			this.planFilePath = "./srcMore/it/unibo/humanoperatorqa1/plans.txt";
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
	    	stateTab.put("sendCmd",sendCmd);
	    }
	    StateFun handleToutBuiltIn = () -> {	
	    	try{	
	    		PlanRepeat pr = PlanRepeat.setUp("handleTout",-1);
	    		String myselfName = "handleToutBuiltIn";  
	    		println( "humanoperatorqa1 tout : stops");  
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
	    	temporaryStr = "\"humanoperatorqa start\"";
	    	println( temporaryStr );  
	    	//switchTo sendCmd
	        switchToPlanAsNextState(pr, myselfName, "humanoperatorqa1_"+myselfName, 
	              "sendCmd",false, false, null); 
	    }catch(Exception e_init){  
	    	 println( getName() + " plan=init WARNING:" + e_init.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//init
	    
	    StateFun sendCmd = () -> {	
	    try{	
	     PlanRepeat pr = PlanRepeat.setUp("sendCmd",-1);
	    	String myselfName = "sendCmd";  
	    	//delay  ( no more reactive within a plan)
	    	aar = delayReactive(400,"" , "");
	    	if( aar.getInterrupted() ) curPlanInExec   = "sendCmd";
	    	if( ! aar.getGoon() ) return ;
	    	temporaryStr = QActorUtils.unifyMsgContent(pengine,"cmd(X)","cmd(w(X))", guardVars ).toString();
	    	sendMsg("cmd","appl1", QActorContext.dispatch, temporaryStr ); 
	    	//delay  ( no more reactive within a plan)
	    	aar = delayReactive(400,"" , "");
	    	if( aar.getInterrupted() ) curPlanInExec   = "sendCmd";
	    	if( ! aar.getGoon() ) return ;
	    	temporaryStr = QActorUtils.unifyMsgContent(pengine,"cmd(X)","cmd(d(X))", guardVars ).toString();
	    	sendMsg("cmd","appl1", QActorContext.dispatch, temporaryStr ); 
	    	repeatPlanNoTransition(pr,myselfName,"humanoperatorqa1_"+myselfName,false,false);
	    }catch(Exception e_sendCmd){  
	    	 println( getName() + " plan=sendCmd WARNING:" + e_sendCmd.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//sendCmd
	    
	    protected void initSensorSystem(){
	    	//doing nothing in a QActor
	    }
	
	}
