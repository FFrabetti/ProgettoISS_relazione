%====================================================================================
% Context ctxLedRasp  SYSTEM-configuration: file it.unibo.ctxLedRasp.ledRasp.pl 
%====================================================================================
context(ctxledrasp, "localhost",  "TCP", "8038" ).  		 
context(ctxapplpa, "localhost",  "TCP", "8018" ).  		 
%%% -------------------------------------------
qactor( ledraspagent , ctxledrasp, "it.unibo.ledraspagent.MsgHandle_Ledraspagent"   ). %%store msgs 
qactor( ledraspagent_ctrl , ctxledrasp, "it.unibo.ledraspagent.Ledraspagent"   ). %%control-driven 
%%% -------------------------------------------
%%% -------------------------------------------

