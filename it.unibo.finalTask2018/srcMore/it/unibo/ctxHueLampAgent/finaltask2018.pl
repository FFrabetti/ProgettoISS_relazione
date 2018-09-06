%====================================================================================
% Context ctxHueLampAgent  SYSTEM-configuration: file it.unibo.ctxHueLampAgent.finalTask2018.pl 
%====================================================================================
context(ctxhuelampagent, "localhost",  "TCP", "8888" ).  		 
%%% -------------------------------------------
qactor( huelampagentold , ctxhuelampagent, "it.unibo.huelampagentold.MsgHandle_Huelampagentold"   ). %%store msgs 
qactor( huelampagentold_ctrl , ctxhuelampagent, "it.unibo.huelampagentold.Huelampagentold"   ). %%control-driven 
qactor( tester , ctxhuelampagent, "it.unibo.tester.MsgHandle_Tester"   ). %%store msgs 
qactor( tester_ctrl , ctxhuelampagent, "it.unibo.tester.Tester"   ). %%control-driven 
%%% -------------------------------------------
%%% -------------------------------------------

