%====================================================================================
% Context ctxNodeRobotTest  SYSTEM-configuration: file it.unibo.ctxNodeRobotTest.nodeRobotTest.pl 
%====================================================================================
context(ctxnoderobottest, "localhost",  "TCP", "1234" ).  		 
%%% -------------------------------------------
qactor( nrtlogger , ctxnoderobottest, "it.unibo.nrtlogger.MsgHandle_Nrtlogger"   ). %%store msgs 
qactor( nrtlogger_ctrl , ctxnoderobottest, "it.unibo.nrtlogger.Nrtlogger"   ). %%control-driven 
%%% -------------------------------------------
eventhandler(evhpalogger,ctxnoderobottest,"it.unibo.ctxNodeRobotTest.Evhpalogger","frontSonar,sonarSensor").  
%%% -------------------------------------------

