%====================================================================================
% Context ctxThermo  SYSTEM-configuration: file it.unibo.ctxThermo.thermo.pl 
%====================================================================================
context(ctxthermo, "localhost",  "TCP", "3232" ).  		 
context(ctxappl, "localhost",  "TCP", "8018" ).  		 
%%% -------------------------------------------
qactor( thermoqa , ctxthermo, "it.unibo.thermoqa.MsgHandle_Thermoqa"   ). %%store msgs 
qactor( thermoqa_ctrl , ctxthermo, "it.unibo.thermoqa.Thermoqa"   ). %%control-driven 
%%% -------------------------------------------
%%% -------------------------------------------

