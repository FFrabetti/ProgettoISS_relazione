%====================================================================================
% Context ctxSwag4  SYSTEM-configuration: file it.unibo.ctxSwag4.finalTask2018.pl 
%====================================================================================
context(ctxswag4, "localhost",  "TCP", "8066" ).  		 
%%% -------------------------------------------
qactor( swag4 , ctxswag4, "it.unibo.swag4.MsgHandle_Swag4"   ). %%store msgs 
qactor( swag4_ctrl , ctxswag4, "it.unibo.swag4.Swag4"   ). %%control-driven 
%%% -------------------------------------------
eventhandler(evhswag4,ctxswag4,"it.unibo.ctxSwag4.Evhswag4","usercmd").  
%%% -------------------------------------------

