%====================================================================================
% Context ctxApplPA  SYSTEM-configuration: file it.unibo.ctxApplPA.finalTask2018.pl 
%====================================================================================
context(ctxapplpa, "localhost",  "TCP", "8018" ).  		 
%%% -------------------------------------------
qactor( controllerpa , ctxapplpa, "it.unibo.controllerpa.MsgHandle_Controllerpa"   ). %%store msgs 
qactor( controllerpa_ctrl , ctxapplpa, "it.unibo.controllerpa.Controllerpa"   ). %%control-driven 
qactor( swagpa , ctxapplpa, "it.unibo.swagpa.MsgHandle_Swagpa"   ). %%store msgs 
qactor( swagpa_ctrl , ctxapplpa, "it.unibo.swagpa.Swagpa"   ). %%control-driven 
qactor( humanoperatorpa , ctxapplpa, "it.unibo.humanoperatorpa.MsgHandle_Humanoperatorpa"   ). %%store msgs 
qactor( humanoperatorpa_ctrl , ctxapplpa, "it.unibo.humanoperatorpa.Humanoperatorpa"   ). %%control-driven 
qactor( clockagentpa , ctxapplpa, "it.unibo.clockagentpa.MsgHandle_Clockagentpa"   ). %%store msgs 
qactor( clockagentpa_ctrl , ctxapplpa, "it.unibo.clockagentpa.Clockagentpa"   ). %%control-driven 
qactor( temperatureagentpa , ctxapplpa, "it.unibo.temperatureagentpa.MsgHandle_Temperatureagentpa"   ). %%store msgs 
qactor( temperatureagentpa_ctrl , ctxapplpa, "it.unibo.temperatureagentpa.Temperatureagentpa"   ). %%control-driven 
qactor( huelampagentpa , ctxapplpa, "it.unibo.huelampagentpa.MsgHandle_Huelampagentpa"   ). %%store msgs 
qactor( huelampagentpa_ctrl , ctxapplpa, "it.unibo.huelampagentpa.Huelampagentpa"   ). %%control-driven 
qactor( palogger , ctxapplpa, "it.unibo.palogger.MsgHandle_Palogger"   ). %%store msgs 
qactor( palogger_ctrl , ctxapplpa, "it.unibo.palogger.Palogger"   ). %%control-driven 
%%% -------------------------------------------
eventhandler(evadapterpa,ctxapplpa,"it.unibo.ctxApplPA.Evadapterpa","temperature,clock").  
eventhandler(evledpa,ctxapplpa,"it.unibo.ctxApplPA.Evledpa","ctrlEvent").  
eventhandler(evhpalogger,ctxapplpa,"it.unibo.ctxApplPA.Evhpalogger","ctrlEvent,lightCmd").  
%%% -------------------------------------------

