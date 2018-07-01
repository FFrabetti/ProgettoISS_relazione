%====================================================================================
% Context ctxThermo  SYSTEM-configuration: file it.unibo.ctxThermo.thermo.pl 
%====================================================================================
context(ctxthermo, "localhost",  "TCP", "2525" ).  		 
context(ctxmvc, "localhost",  "TCP", "8019" ).  		 
%%% -------------------------------------------
qactor( thermoqa , ctxthermo, "it.unibo.thermoqa.MsgHandle_Thermoqa"   ). %%store msgs 
qactor( thermoqa_ctrl , ctxthermo, "it.unibo.thermoqa.Thermoqa"   ). %%control-driven 
%%% -------------------------------------------
%%% -------------------------------------------

