%====================================================================================
% Context ctxSwag3  SYSTEM-configuration: file it.unibo.ctxSwag3.finalTask2018.pl 
%====================================================================================
context(ctxswag3, "localhost",  "TCP", "8066" ).  		 
%%% -------------------------------------------
qactor( swag3 , ctxswag3, "it.unibo.swag3.MsgHandle_Swag3"   ). %%store msgs 
qactor( swag3_ctrl , ctxswag3, "it.unibo.swag3.Swag3"   ). %%control-driven 
%%% -------------------------------------------
%%% -------------------------------------------

