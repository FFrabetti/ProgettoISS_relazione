%====================================================================================
% Context ctxSwagFinalForm  SYSTEM-configuration: file it.unibo.ctxSwagFinalForm.finalTask2018.pl 
%====================================================================================
context(ctxswagfinalform, "localhost",  "TCP", "8066" ).  		 
context(ctxvirtualrobotnode, "localhost",  "TCP", "8822" ).  		 
%%% -------------------------------------------
qactor( swagff , ctxswagfinalform, "it.unibo.swagff.MsgHandle_Swagff"   ). %%store msgs 
qactor( swagff_ctrl , ctxswagfinalform, "it.unibo.swagff.Swagff"   ). %%control-driven 
%%% -------------------------------------------
eventhandler(evhswagff,ctxswagfinalform,"it.unibo.ctxSwagFinalForm.Evhswagff","usercmd").  
%%% -------------------------------------------

