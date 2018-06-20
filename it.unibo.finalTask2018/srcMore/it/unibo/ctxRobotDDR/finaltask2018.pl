%====================================================================================
% Context ctxRobotDDR  SYSTEM-configuration: file it.unibo.ctxRobotDDR.finalTask2018.pl 
%====================================================================================
context(ctxrobotddr, "localhost",  "TCP", "8882" ).  		 
%%% -------------------------------------------
qactor( robotddr , ctxrobotddr, "it.unibo.robotddr.MsgHandle_Robotddr"   ). %%store msgs 
qactor( robotddr_ctrl , ctxrobotddr, "it.unibo.robotddr.Robotddr"   ). %%control-driven 
qactor( testerrobot , ctxrobotddr, "it.unibo.testerrobot.MsgHandle_Testerrobot"   ). %%store msgs 
qactor( testerrobot_ctrl , ctxrobotddr, "it.unibo.testerrobot.Testerrobot"   ). %%control-driven 
%%% -------------------------------------------
%%% -------------------------------------------

