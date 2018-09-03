%====================================================================================
% Context ctxRaIntegrator  SYSTEM-configuration: file it.unibo.ctxRaIntegrator.raintegrator.pl 
%====================================================================================
context(ctxraintegrator, "localhost",  "TCP", "8880" ).  		 
context(ctxddr, "localhost",  "TCP", "8882" ).  		 
context(ctxreqanalysis, "localhost",  "TCP", "8888" ).  		 
%%% -------------------------------------------
qactor( raintegratorqa , ctxraintegrator, "it.unibo.raintegratorqa.MsgHandle_Raintegratorqa"   ). %%store msgs 
qactor( raintegratorqa_ctrl , ctxraintegrator, "it.unibo.raintegratorqa.Raintegratorqa"   ). %%control-driven 
%%% -------------------------------------------
%%% -------------------------------------------

