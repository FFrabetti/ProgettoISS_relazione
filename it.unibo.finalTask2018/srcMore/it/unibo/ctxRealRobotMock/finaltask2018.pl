%====================================================================================
% Context ctxRealRobotMock  SYSTEM-configuration: file it.unibo.ctxRealRobotMock.finalTask2018.pl 
%====================================================================================
context(ctxrealrobotmock, "localhost",  "TCP", "8866" ).  		 
context(ctxapplpa, "localhost",  "TCP", "8018" ).  		 
%%% -------------------------------------------
qactor( realrobotmock , ctxrealrobotmock, "it.unibo.realrobotmock.MsgHandle_Realrobotmock"   ). %%store msgs 
qactor( realrobotmock_ctrl , ctxrealrobotmock, "it.unibo.realrobotmock.Realrobotmock"   ). %%control-driven 
qactor( ledrobotmock , ctxrealrobotmock, "it.unibo.ledrobotmock.MsgHandle_Ledrobotmock"   ). %%store msgs 
qactor( ledrobotmock_ctrl , ctxrealrobotmock, "it.unibo.ledrobotmock.Ledrobotmock"   ). %%control-driven 
%%% -------------------------------------------
eventhandler(evhrealmock,ctxrealrobotmock,"it.unibo.ctxRealRobotMock.Evhrealmock","ctrlEvent").  
eventhandler(evhrealmockbroker,ctxrealrobotmock,"it.unibo.ctxRealRobotMock.Evhrealmockbroker","local_robotCmd").  
%%% -------------------------------------------

