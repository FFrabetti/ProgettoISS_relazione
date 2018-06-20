%====================================================================================
% Context ctxHueLampAgent  SYSTEM-configuration: file it.unibo.ctxHueLampAgent.finalTask2018.pl 
%====================================================================================
context(ctxhuelampagent, "localhost",  "TCP", "8888" ).  		 
%%% -------------------------------------------
qactor( huelampagent , ctxhuelampagent, "it.unibo.huelampagent.MsgHandle_Huelampagent"   ). %%store msgs 
qactor( huelampagent_ctrl , ctxhuelampagent, "it.unibo.huelampagent.Huelampagent"   ). %%control-driven 
qactor( tester , ctxhuelampagent, "it.unibo.tester.MsgHandle_Tester"   ). %%store msgs 
qactor( tester_ctrl , ctxhuelampagent, "it.unibo.tester.Tester"   ). %%control-driven 
%%% -------------------------------------------
%%% -------------------------------------------

