%====================================================================================
% Context mvcCtx  SYSTEM-configuration: file it.unibo.mvcCtx.mvc.pl 
%====================================================================================
context(mvcctx, "localhost",  "TCP", "8019" ).  		 
%%% -------------------------------------------
qactor( mvccontroller , mvcctx, "it.unibo.mvccontroller.MsgHandle_Mvccontroller"   ). %%store msgs 
qactor( mvccontroller_ctrl , mvcctx, "it.unibo.mvccontroller.Mvccontroller"   ). %%control-driven 
qactor( robotmock , mvcctx, "it.unibo.robotmock.MsgHandle_Robotmock"   ). %%store msgs 
qactor( robotmock_ctrl , mvcctx, "it.unibo.robotmock.Robotmock"   ). %%control-driven 
%%% -------------------------------------------
eventhandler(evadapter,mvcctx,"it.unibo.mvcCtx.Evadapter","temperature,clock").  
%%% -------------------------------------------

