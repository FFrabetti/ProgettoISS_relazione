%====================================================================================
% Context ctxHumanOperator  SYSTEM-configuration: file it.unibo.ctxHumanOperator.finalTask2018.pl 
%====================================================================================
context(ctxhumanoperator, "localhost",  "TCP", "8010" ).  		 
%%% -------------------------------------------
qactor( humanoperatorqa , ctxhumanoperator, "it.unibo.humanoperatorqa.MsgHandle_Humanoperatorqa"   ). %%store msgs 
qactor( humanoperatorqa_ctrl , ctxhumanoperator, "it.unibo.humanoperatorqa.Humanoperatorqa"   ). %%control-driven 
qactor( testapplqa , ctxhumanoperator, "it.unibo.testapplqa.MsgHandle_Testapplqa"   ). %%store msgs 
qactor( testapplqa_ctrl , ctxhumanoperator, "it.unibo.testapplqa.Testapplqa"   ). %%control-driven 
%%% -------------------------------------------
%%% -------------------------------------------

