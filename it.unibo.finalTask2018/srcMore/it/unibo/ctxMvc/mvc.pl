%====================================================================================
% Context ctxMvc  SYSTEM-configuration: file it.unibo.ctxMvc.mvc.pl 
%====================================================================================
context(ctxmvc, "localhost",  "TCP", "8019" ).  		 
%%% -------------------------------------------
qactor( mvccontroller , ctxmvc, "it.unibo.mvccontroller.MsgHandle_Mvccontroller"   ). %%store msgs 
qactor( mvccontroller_ctrl , ctxmvc, "it.unibo.mvccontroller.Mvccontroller"   ). %%control-driven 
qactor( swag , ctxmvc, "it.unibo.swag.MsgHandle_Swag"   ). %%store msgs 
qactor( swag_ctrl , ctxmvc, "it.unibo.swag.Swag"   ). %%control-driven 
%%% -------------------------------------------
eventhandler(evadapter,ctxmvc,"it.unibo.ctxMvc.Evadapter","temperature,clock").  
%%% -------------------------------------------

