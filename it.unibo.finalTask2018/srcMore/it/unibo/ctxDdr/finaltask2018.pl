%====================================================================================
% Context ctxDdr  SYSTEM-configuration: file it.unibo.ctxDdr.finalTask2018.pl 
%====================================================================================
context(ctxddr, "localhost",  "TCP", "8882" ).  		 
%%% -------------------------------------------
qactor( ddr , ctxddr, "it.unibo.ddr.MsgHandle_Ddr"   ). %%store msgs 
qactor( ddr_ctrl , ctxddr, "it.unibo.ddr.Ddr"   ). %%control-driven 
qactor( testerrobot , ctxddr, "it.unibo.testerrobot.MsgHandle_Testerrobot"   ). %%store msgs 
qactor( testerrobot_ctrl , ctxddr, "it.unibo.testerrobot.Testerrobot"   ). %%control-driven 
%%% -------------------------------------------
%%% -------------------------------------------

