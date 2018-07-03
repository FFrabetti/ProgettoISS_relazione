%====================================================================================
% Context ctxRealRobotMock  SYSTEM-configuration: file it.unibo.ctxRealRobotMock.finalTask2018.pl 
%====================================================================================
context(ctxrealrobotmock, "localhost",  "TCP", "8866" ).  		 
context(ctxapplpa, "localhost",  "TCP", "8018" ).  		 
%%% -------------------------------------------
qactor( realrobotmock , ctxrealrobotmock, "it.unibo.realrobotmock.MsgHandle_Realrobotmock"   ). %%store msgs 
qactor( realrobotmock_ctrl , ctxrealrobotmock, "it.unibo.realrobotmock.Realrobotmock"   ). %%control-driven 
qactor( realrobotbroker , ctxrealrobotmock, "it.unibo.realrobotbroker.MsgHandle_Realrobotbroker"   ). %%store msgs 
qactor( realrobotbroker_ctrl , ctxrealrobotmock, "it.unibo.realrobotbroker.Realrobotbroker"   ). %%control-driven 
%%% -------------------------------------------
eventhandler(evhnode,ctxrealrobotmock,"it.unibo.ctxRealRobotMock.Evhnode","ctrlEvent").  
%%% -------------------------------------------

