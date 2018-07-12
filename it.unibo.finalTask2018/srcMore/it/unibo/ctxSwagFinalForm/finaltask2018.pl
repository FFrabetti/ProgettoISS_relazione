%====================================================================================
% Context ctxSwagFinalForm  SYSTEM-configuration: file it.unibo.ctxSwagFinalForm.finalTask2018.pl 
%====================================================================================
context(ctxswagfinalform, "localhost",  "TCP", "8066" ).  		 
context(ctxvirtualrobotnode, "localhost",  "TCP", "8822" ).  		 
%%% -------------------------------------------
qactor( swagff , ctxswagfinalform, "it.unibo.swagff.MsgHandle_Swagff"   ). %%store msgs 
qactor( swagff_ctrl , ctxswagfinalform, "it.unibo.swagff.Swagff"   ). %%control-driven 
qactor( testertest , ctxswagfinalform, "it.unibo.testertest.MsgHandle_Testertest"   ). %%store msgs 
qactor( testertest_ctrl , ctxswagfinalform, "it.unibo.testertest.Testertest"   ). %%control-driven 
%%% -------------------------------------------
eventhandler(evhswagff,ctxswagfinalform,"it.unibo.ctxSwagFinalForm.Evhswagff","usercmd").  
%%% -------------------------------------------

