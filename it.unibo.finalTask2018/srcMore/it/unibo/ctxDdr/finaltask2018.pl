%====================================================================================
% Context ctxDdr  SYSTEM-configuration: file it.unibo.ctxDdr.finalTask2018.pl 
%====================================================================================
context(ctxddr, "localhost",  "TCP", "8882" ).  		 
%%% -------------------------------------------
qactor( ddr , ctxddr, "it.unibo.ddr.MsgHandle_Ddr"   ). %%store msgs 
qactor( ddr_ctrl , ctxddr, "it.unibo.ddr.Ddr"   ). %%control-driven 
qactor( ddrlogger , ctxddr, "it.unibo.ddrlogger.MsgHandle_Ddrlogger"   ). %%store msgs 
qactor( ddrlogger_ctrl , ctxddr, "it.unibo.ddrlogger.Ddrlogger"   ). %%control-driven 
%%% -------------------------------------------
eventhandler(evhddr,ctxddr,"it.unibo.ctxDdr.Evhddr","robotCmd").  
eventhandler(evhddrlogger,ctxddr,"it.unibo.ctxDdr.Evhddrlogger","sonarSensor,frontSonar,robotCmd,lightCmd").  
%%% -------------------------------------------

