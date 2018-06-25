%====================================================================================
% Context ctxReqAnalysis  SYSTEM-configuration: file it.unibo.ctxReqAnalysis.finalTask2018.pl 
%====================================================================================
context(ctxreqanalysis, "localhost",  "TCP", "8888" ).  		 
%%% -------------------------------------------
qactor( clockagent1 , ctxreqanalysis, "it.unibo.clockagent1.MsgHandle_Clockagent1"   ). %%store msgs 
qactor( clockagent1_ctrl , ctxreqanalysis, "it.unibo.clockagent1.Clockagent1"   ). %%control-driven 
qactor( temperatureagent1 , ctxreqanalysis, "it.unibo.temperatureagent1.MsgHandle_Temperatureagent1"   ). %%store msgs 
qactor( temperatureagent1_ctrl , ctxreqanalysis, "it.unibo.temperatureagent1.Temperatureagent1"   ). %%control-driven 
qactor( robotddr1 , ctxreqanalysis, "it.unibo.robotddr1.MsgHandle_Robotddr1"   ). %%store msgs 
qactor( robotddr1_ctrl , ctxreqanalysis, "it.unibo.robotddr1.Robotddr1"   ). %%control-driven 
qactor( huelampagent1 , ctxreqanalysis, "it.unibo.huelampagent1.MsgHandle_Huelampagent1"   ). %%store msgs 
qactor( huelampagent1_ctrl , ctxreqanalysis, "it.unibo.huelampagent1.Huelampagent1"   ). %%control-driven 
qactor( humanoperatorqa1 , ctxreqanalysis, "it.unibo.humanoperatorqa1.MsgHandle_Humanoperatorqa1"   ). %%store msgs 
qactor( humanoperatorqa1_ctrl , ctxreqanalysis, "it.unibo.humanoperatorqa1.Humanoperatorqa1"   ). %%control-driven 
qactor( appl1 , ctxreqanalysis, "it.unibo.appl1.MsgHandle_Appl1"   ). %%store msgs 
qactor( appl1_ctrl , ctxreqanalysis, "it.unibo.appl1.Appl1"   ). %%control-driven 
%%% -------------------------------------------
%%% -------------------------------------------

