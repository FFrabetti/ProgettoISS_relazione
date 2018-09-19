%====================================================================================
% Context ctxAppl  SYSTEM-configuration: file it.unibo.ctxAppl.mockappl.pl 
%====================================================================================
context(ctxappl, "10.0.3.3",  "TCP", "8018" ).  		 
%%% -------------------------------------------
qactor( testappl , ctxappl, "it.unibo.testappl.MsgHandle_Testappl"   ). %%store msgs 
qactor( testappl_ctrl , ctxappl, "it.unibo.testappl.Testappl"   ). %%control-driven 
%%% -------------------------------------------
%%% -------------------------------------------

