%====================================================================================
% Context ctxTemperatureAgent  SYSTEM-configuration: file it.unibo.ctxTemperatureAgent.finalTask2018.pl 
%====================================================================================
context(ctxtemperatureagent, "localhost",  "TCP", "8886" ).  		 
%%% -------------------------------------------
qactor( temperatureagent , ctxtemperatureagent, "it.unibo.temperatureagent.MsgHandle_Temperatureagent"   ). %%store msgs 
qactor( temperatureagent_ctrl , ctxtemperatureagent, "it.unibo.temperatureagent.Temperatureagent"   ). %%control-driven 
qactor( testertemp , ctxtemperatureagent, "it.unibo.testertemp.MsgHandle_Testertemp"   ). %%store msgs 
qactor( testertemp_ctrl , ctxtemperatureagent, "it.unibo.testertemp.Testertemp"   ). %%control-driven 
%%% -------------------------------------------
%%% -------------------------------------------

