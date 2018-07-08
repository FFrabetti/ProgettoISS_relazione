%====================================================================================
% Context ctxSwag2  SYSTEM-configuration: file it.unibo.ctxSwag2.finalTask2018.pl 
%====================================================================================
context(ctxswag2, "localhost",  "TCP", "8016" ).  		 
%%% -------------------------------------------
qactor( swag2pa , ctxswag2, "it.unibo.swag2pa.MsgHandle_Swag2pa"   ). %%store msgs 
qactor( swag2pa_ctrl , ctxswag2, "it.unibo.swag2pa.Swag2pa"   ). %%control-driven 
qactor( swag2tester , ctxswag2, "it.unibo.swag2tester.MsgHandle_Swag2tester"   ). %%store msgs 
qactor( swag2tester_ctrl , ctxswag2, "it.unibo.swag2tester.Swag2tester"   ). %%control-driven 
%%% -------------------------------------------
eventhandler(evhswag2pa,ctxswag2,"it.unibo.ctxSwag2.Evhswag2pa","usercmd").  
%%% -------------------------------------------

