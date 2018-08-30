%====================================================================================
% Context ctxAppl  SYSTEM-configuration: file it.unibo.ctxAppl.finalTask2018.pl 
%====================================================================================
context(ctxappl, "localhost",  "TCP", "8018" ).  		 
%%% -------------------------------------------
qactor( controller , ctxappl, "it.unibo.controller.MsgHandle_Controller"   ). %%store msgs 
qactor( controller_ctrl , ctxappl, "it.unibo.controller.Controller"   ). %%control-driven 
qactor( swag , ctxappl, "it.unibo.swag.MsgHandle_Swag"   ). %%store msgs 
qactor( swag_ctrl , ctxappl, "it.unibo.swag.Swag"   ). %%control-driven 
qactor( humanoperator , ctxappl, "it.unibo.humanoperator.MsgHandle_Humanoperator"   ). %%store msgs 
qactor( humanoperator_ctrl , ctxappl, "it.unibo.humanoperator.Humanoperator"   ). %%control-driven 
qactor( clockagent , ctxappl, "it.unibo.clockagent.MsgHandle_Clockagent"   ). %%store msgs 
qactor( clockagent_ctrl , ctxappl, "it.unibo.clockagent.Clockagent"   ). %%control-driven 
qactor( temperatureagent , ctxappl, "it.unibo.temperatureagent.MsgHandle_Temperatureagent"   ). %%store msgs 
qactor( temperatureagent_ctrl , ctxappl, "it.unibo.temperatureagent.Temperatureagent"   ). %%control-driven 
qactor( huelampagent , ctxappl, "it.unibo.huelampagent.MsgHandle_Huelampagent"   ). %%store msgs 
qactor( huelampagent_ctrl , ctxappl, "it.unibo.huelampagent.Huelampagent"   ). %%control-driven 
qactor( crslogger , ctxappl, "it.unibo.crslogger.MsgHandle_Crslogger"   ). %%store msgs 
qactor( crslogger_ctrl , ctxappl, "it.unibo.crslogger.Crslogger"   ). %%control-driven 
%%% -------------------------------------------
eventhandler(evadapter,ctxappl,"it.unibo.ctxAppl.Evadapter","temperature,clock").  
eventhandler(evled,ctxappl,"it.unibo.ctxAppl.Evled","ctrlEvent").  
eventhandler(evhswag,ctxappl,"it.unibo.ctxAppl.Evhswag","alarm").  
%%% -------------------------------------------

