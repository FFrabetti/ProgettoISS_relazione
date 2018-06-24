%====================================================================================
% Context ctxLedMockGui  SYSTEM-configuration: file it.unibo.ctxLedMockGui.ledMockGui.pl 
%====================================================================================
context(ctxledmockgui, "localhost",  "TCP", "8039" ).  		 
%%% -------------------------------------------
qactor( ledmockgui , ctxledmockgui, "it.unibo.ledmockgui.MsgHandle_Ledmockgui"   ). %%store msgs 
qactor( ledmockgui_ctrl , ctxledmockgui, "it.unibo.ledmockgui.Ledmockgui"   ). %%control-driven 
qactor( testeventemitter , ctxledmockgui, "it.unibo.testeventemitter.MsgHandle_Testeventemitter"   ). %%store msgs 
qactor( testeventemitter_ctrl , ctxledmockgui, "it.unibo.testeventemitter.Testeventemitter"   ). %%control-driven 
%%% -------------------------------------------
%%% -------------------------------------------

