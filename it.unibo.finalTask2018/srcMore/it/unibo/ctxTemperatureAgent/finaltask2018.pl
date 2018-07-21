%====================================================================================
% Context ctxTemperatureAgent  SYSTEM-configuration: file it.unibo.ctxTemperatureAgent.finalTask2018.pl 
%====================================================================================
context(ctxtemperatureagent, "localhost",  "TCP", "8886" ).  		 
context(ctxmvc, "localhost",  "TCP", "8019" ).  		 
%%% -------------------------------------------
qactor( temperatureagentold , ctxtemperatureagent, "it.unibo.temperatureagentold.MsgHandle_Temperatureagentold"   ). %%store msgs 
qactor( temperatureagentold_ctrl , ctxtemperatureagent, "it.unibo.temperatureagentold.Temperatureagentold"   ). %%control-driven 
qactor( testertemp , ctxtemperatureagent, "it.unibo.testertemp.MsgHandle_Testertemp"   ). %%store msgs 
qactor( testertemp_ctrl , ctxtemperatureagent, "it.unibo.testertemp.Testertemp"   ). %%control-driven 
%%% -------------------------------------------
%%% -------------------------------------------

