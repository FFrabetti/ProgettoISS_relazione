%====================================================================================
% Context ctxLedMockGui  SYSTEM-configuration: file it.unibo.ctxLedMockGui.ledMockGui.pl 
%====================================================================================
context(ctxledmockgui, "localhost",  "TCP", "8039" ).  		 
context(ctxmvc, "localhost",  "TCP", "8019" ).  		 
%%% -------------------------------------------
qactor( ledmockgui , ctxledmockgui, "it.unibo.ledmockgui.MsgHandle_Ledmockgui"   ). %%store msgs 
qactor( ledmockgui_ctrl , ctxledmockgui, "it.unibo.ledmockgui.Ledmockgui"   ). %%control-driven 
%%% -------------------------------------------
%%% -------------------------------------------

