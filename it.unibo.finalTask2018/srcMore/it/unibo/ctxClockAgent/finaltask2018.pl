%====================================================================================
% Context ctxClockAgent  SYSTEM-configuration: file it.unibo.ctxClockAgent.finalTask2018.pl 
%====================================================================================
context(ctxclockagent, "localhost",  "TCP", "8884" ).  		 
context(ctxmvc, "localhost",  "TCP", "8019" ).  		 
%%% -------------------------------------------
qactor( clockagentold , ctxclockagent, "it.unibo.clockagentold.MsgHandle_Clockagentold"   ). %%store msgs 
qactor( clockagentold_ctrl , ctxclockagent, "it.unibo.clockagentold.Clockagentold"   ). %%control-driven 
%%% -------------------------------------------
%%% -------------------------------------------

