%====================================================================================
% Context ctxClockAgent  SYSTEM-configuration: file it.unibo.ctxClockAgent.finalTask2018.pl 
%====================================================================================
context(ctxclockagent, "localhost",  "TCP", "8884" ).  		 
context(ctxmvc, "localhost",  "TCP", "8019" ).  		 
%%% -------------------------------------------
qactor( clockagent , ctxclockagent, "it.unibo.clockagent.MsgHandle_Clockagent"   ). %%store msgs 
qactor( clockagent_ctrl , ctxclockagent, "it.unibo.clockagent.Clockagent"   ). %%control-driven 
%%% -------------------------------------------
%%% -------------------------------------------

