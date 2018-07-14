%====================================================================================
% Context ctxVirtualRobotNode  SYSTEM-configuration: file it.unibo.ctxVirtualRobotNode.finalTask2018.pl 
%====================================================================================
context(ctxvirtualrobotnode, "localhost",  "TCP", "8822" ).  		 
context(ctxappl, "localhost",  "TCP", "8018" ).  		 
%%% -------------------------------------------
qactor( robotnode , ctxvirtualrobotnode, "it.unibo.robotnode.MsgHandle_Robotnode"   ). %%store msgs 
qactor( robotnode_ctrl , ctxvirtualrobotnode, "it.unibo.robotnode.Robotnode"   ). %%control-driven 
qactor( nodebroker , ctxvirtualrobotnode, "it.unibo.nodebroker.MsgHandle_Nodebroker"   ). %%store msgs 
qactor( nodebroker_ctrl , ctxvirtualrobotnode, "it.unibo.nodebroker.Nodebroker"   ). %%control-driven 
%%% -------------------------------------------
eventhandler(evhnode,ctxvirtualrobotnode,"it.unibo.ctxVirtualRobotNode.Evhnode","ctrlEvent").  
%%% -------------------------------------------

