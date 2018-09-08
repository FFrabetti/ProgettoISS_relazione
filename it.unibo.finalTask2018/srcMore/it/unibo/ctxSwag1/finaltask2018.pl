%====================================================================================
% Context ctxSwag1  SYSTEM-configuration: file it.unibo.ctxSwag1.finalTask2018.pl 
%====================================================================================
context(ctxswag1, "localhost",  "TCP", "8016" ).  		 
%%% -------------------------------------------
qactor( swag1 , ctxswag1, "it.unibo.swag1.MsgHandle_Swag1"   ). %%store msgs 
qactor( swag1_ctrl , ctxswag1, "it.unibo.swag1.Swag1"   ). %%control-driven 
%%% -------------------------------------------
eventhandler(evhswag1,ctxswag1,"it.unibo.ctxSwag1.Evhswag1","usercmd,alarm").  
%%% -------------------------------------------

