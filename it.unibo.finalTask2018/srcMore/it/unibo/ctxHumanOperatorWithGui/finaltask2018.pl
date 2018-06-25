%====================================================================================
% Context ctxHumanOperatorWithGui  SYSTEM-configuration: file it.unibo.ctxHumanOperatorWithGui.finalTask2018.pl 
%====================================================================================
context(ctxhumanoperatorwithgui, "localhost",  "TCP", "8020" ).  		 
context(ctxmvc, "localhost",  "TCP", "8019" ).  		 
%%% -------------------------------------------
qactor( humanoperatorwithgui , ctxhumanoperatorwithgui, "it.unibo.humanoperatorwithgui.MsgHandle_Humanoperatorwithgui"   ). %%store msgs 
qactor( humanoperatorwithgui_ctrl , ctxhumanoperatorwithgui, "it.unibo.humanoperatorwithgui.Humanoperatorwithgui"   ). %%control-driven 
%%% -------------------------------------------
%%% -------------------------------------------

