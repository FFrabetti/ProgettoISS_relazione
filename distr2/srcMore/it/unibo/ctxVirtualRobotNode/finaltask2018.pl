%====================================================================================
% Context ctxVirtualRobotNode  SYSTEM-configuration: file it.unibo.ctxVirtualRobotNode.finalTask2018.pl 
%====================================================================================
context(ctxvirtualrobotnode, "192.168.43.3",  "TCP", "8822" ).  		 
context(ctxappl, "192.168.43.3",  "TCP", "8018" ).  		 
%%% -------------------------------------------
qactor( robotnode , ctxvirtualrobotnode, "it.unibo.robotnode.MsgHandle_Robotnode"   ). %%store msgs 
qactor( robotnode_ctrl , ctxvirtualrobotnode, "it.unibo.robotnode.Robotnode"   ). %%control-driven 
%%% -------------------------------------------
eventhandler(evhnode,ctxvirtualrobotnode,"it.unibo.ctxVirtualRobotNode.Evhnode","ctrlEvent").  
eventhandler(evhnodebroker,ctxvirtualrobotnode,"it.unibo.ctxVirtualRobotNode.Evhnodebroker","local_robotCmd").  
%%% -------------------------------------------

