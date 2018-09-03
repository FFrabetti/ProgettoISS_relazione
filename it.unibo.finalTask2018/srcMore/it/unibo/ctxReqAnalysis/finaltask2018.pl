%====================================================================================
% Context ctxReqAnalysis  SYSTEM-configuration: file it.unibo.ctxReqAnalysis.finalTask2018.pl 
%====================================================================================
context(ctxreqanalysis, "localhost",  "TCP", "8888" ).  		 
%%% -------------------------------------------
qactor( huelampagentra , ctxreqanalysis, "it.unibo.huelampagentra.MsgHandle_Huelampagentra"   ). %%store msgs 
qactor( huelampagentra_ctrl , ctxreqanalysis, "it.unibo.huelampagentra.Huelampagentra"   ). %%control-driven 
qactor( temperatureagentra , ctxreqanalysis, "it.unibo.temperatureagentra.MsgHandle_Temperatureagentra"   ). %%store msgs 
qactor( temperatureagentra_ctrl , ctxreqanalysis, "it.unibo.temperatureagentra.Temperatureagentra"   ). %%control-driven 
qactor( clockagentra , ctxreqanalysis, "it.unibo.clockagentra.MsgHandle_Clockagentra"   ). %%store msgs 
qactor( clockagentra_ctrl , ctxreqanalysis, "it.unibo.clockagentra.Clockagentra"   ). %%control-driven 
qactor( humanoperatorra , ctxreqanalysis, "it.unibo.humanoperatorra.MsgHandle_Humanoperatorra"   ). %%store msgs 
qactor( humanoperatorra_ctrl , ctxreqanalysis, "it.unibo.humanoperatorra.Humanoperatorra"   ). %%control-driven 
qactor( applra , ctxreqanalysis, "it.unibo.applra.MsgHandle_Applra"   ). %%store msgs 
qactor( applra_ctrl , ctxreqanalysis, "it.unibo.applra.Applra"   ). %%control-driven 
qactor( ralogger , ctxreqanalysis, "it.unibo.ralogger.MsgHandle_Ralogger"   ). %%store msgs 
qactor( ralogger_ctrl , ctxreqanalysis, "it.unibo.ralogger.Ralogger"   ). %%control-driven 
%%% -------------------------------------------
eventhandler(evhralogger,ctxreqanalysis,"it.unibo.ctxReqAnalysis.Evhralogger","clock,temperature,sonarSensor,frontSonar,robotCmd,lightCmd").  
%%% -------------------------------------------

