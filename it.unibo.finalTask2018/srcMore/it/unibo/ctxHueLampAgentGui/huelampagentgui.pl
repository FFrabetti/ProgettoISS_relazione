%====================================================================================
% Context ctxHueLampAgentGui  SYSTEM-configuration: file it.unibo.ctxHueLampAgentGui.hueLampAgentGui.pl 
%====================================================================================
context(ctxhuelampagentgui, "localhost",  "TCP", "8039" ).  		 
context(ctxmvc, "localhost",  "TCP", "8019" ).  		 
%%% -------------------------------------------
qactor( huelampagentgui , ctxhuelampagentgui, "it.unibo.huelampagentgui.MsgHandle_Huelampagentgui"   ). %%store msgs 
qactor( huelampagentgui_ctrl , ctxhuelampagentgui, "it.unibo.huelampagentgui.Huelampagentgui"   ). %%control-driven 
%%% -------------------------------------------
eventhandler(evled,ctxhuelampagentgui,"it.unibo.ctxHueLampAgentGui.Evled","ctrlEvent").  
%%% -------------------------------------------

