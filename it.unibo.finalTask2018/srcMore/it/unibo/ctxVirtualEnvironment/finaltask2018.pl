%====================================================================================
% Context ctxVirtualEnvironment  SYSTEM-configuration: file it.unibo.ctxVirtualEnvironment.finalTask2018.pl 
%====================================================================================
context(ctxvirtualenvironment, "localhost",  "TCP", "8882" ).  		 
%%% -------------------------------------------
qactor( virtualenv , ctxvirtualenvironment, "it.unibo.virtualenv.MsgHandle_Virtualenv"   ). %%store msgs 
qactor( virtualenv_ctrl , ctxvirtualenvironment, "it.unibo.virtualenv.Virtualenv"   ). %%control-driven 
qactor( testerenv , ctxvirtualenvironment, "it.unibo.testerenv.MsgHandle_Testerenv"   ). %%store msgs 
qactor( testerenv_ctrl , ctxvirtualenvironment, "it.unibo.testerenv.Testerenv"   ). %%control-driven 
%%% -------------------------------------------
%%% -------------------------------------------

