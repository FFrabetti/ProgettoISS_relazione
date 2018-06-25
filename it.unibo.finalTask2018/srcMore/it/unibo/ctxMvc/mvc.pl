%====================================================================================
% Context ctxMvc  SYSTEM-configuration: file it.unibo.ctxMvc.mvc.pl 
%====================================================================================
context(ctxmvc, "localhost",  "TCP", "8019" ).  		 
%%% -------------------------------------------
qactor( mvccontroller , ctxmvc, "it.unibo.mvccontroller.MsgHandle_Mvccontroller"   ). %%store msgs 
qactor( mvccontroller_ctrl , ctxmvc, "it.unibo.mvccontroller.Mvccontroller"   ). %%control-driven 
qactor( robotmock , ctxmvc, "it.unibo.robotmock.MsgHandle_Robotmock"   ). %%store msgs 
qactor( robotmock_ctrl , ctxmvc, "it.unibo.robotmock.Robotmock"   ). %%control-driven 
%%% -------------------------------------------
eventhandler(evadapter,ctxmvc,"it.unibo.ctxMvc.Evadapter","temperature,clock").  
%%% -------------------------------------------

