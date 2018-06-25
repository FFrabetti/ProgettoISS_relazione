%====================================================================================
% Context ctxVirtualRobotNode  SYSTEM-configuration: file it.unibo.ctxVirtualRobotNode.finalTask2018.pl 
%====================================================================================
context(ctxvirtualrobotnode, "localhost",  "TCP", "8822" ).  		 
%%% -------------------------------------------
qactor( robotnode , ctxvirtualrobotnode, "it.unibo.robotnode.MsgHandle_Robotnode"   ). %%store msgs 
qactor( robotnode_ctrl , ctxvirtualrobotnode, "it.unibo.robotnode.Robotnode"   ). %%control-driven 
qactor( testerrobotnode , ctxvirtualrobotnode, "it.unibo.testerrobotnode.MsgHandle_Testerrobotnode"   ). %%store msgs 
qactor( testerrobotnode_ctrl , ctxvirtualrobotnode, "it.unibo.testerrobotnode.Testerrobotnode"   ). %%control-driven 
%%% -------------------------------------------
%%% -------------------------------------------

