%====================================================================================
% Context ctxFrontendActivator  SYSTEM-configuration: file it.unibo.ctxFrontendActivator.frontendActivator.pl 
%====================================================================================
context(ctxfrontendactivator, "192.168.43.3",  "TCP", "3434" ).  		 
context(ctxappl, "192.168.43.3",  "TCP", "8018" ).  		 
%%% -------------------------------------------
qactor( feactivator , ctxfrontendactivator, "it.unibo.feactivator.MsgHandle_Feactivator"   ). %%store msgs 
qactor( feactivator_ctrl , ctxfrontendactivator, "it.unibo.feactivator.Feactivator"   ). %%control-driven 
qactor( febroker , ctxfrontendactivator, "it.unibo.febroker.MsgHandle_Febroker"   ). %%store msgs 
qactor( febroker_ctrl , ctxfrontendactivator, "it.unibo.febroker.Febroker"   ). %%control-driven 
qactor( fetester , ctxfrontendactivator, "it.unibo.fetester.MsgHandle_Fetester"   ). %%store msgs 
qactor( fetester_ctrl , ctxfrontendactivator, "it.unibo.fetester.Fetester"   ). %%control-driven 
%%% -------------------------------------------
eventhandler(evhfe,ctxfrontendactivator,"it.unibo.ctxFrontendActivator.Evhfe","ctrlEvent").  
eventhandler(evhfebroker,ctxfrontendactivator,"it.unibo.ctxFrontendActivator.Evhfebroker","local_robotCmd").  
%%% -------------------------------------------

