%====================================================================================
% Context ctxNodeEnvironment  SYSTEM-configuration: file it.unibo.ctxNodeEnvironment.finalTask2018.pl 
%====================================================================================
context(ctxnodeenvironment, "localhost",  "TCP", "8882" ).  		 
%%% -------------------------------------------
qactor( nodeenv , ctxnodeenvironment, "it.unibo.nodeenv.MsgHandle_Nodeenv"   ). %%store msgs 
qactor( nodeenv_ctrl , ctxnodeenvironment, "it.unibo.nodeenv.Nodeenv"   ). %%control-driven 
%%% -------------------------------------------
%%% -------------------------------------------

