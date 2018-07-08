%====================================================================================
% Context ctx  SYSTEM-configuration: file it.unibo.ctx.acazzo.pl 
%====================================================================================
context(ctx, "localhost",  "TCP", "8888" ).  		 
%%% -------------------------------------------
qactor( html , ctx, "it.unibo.html.MsgHandle_Html"   ). %%store msgs 
qactor( html_ctrl , ctx, "it.unibo.html.Html"   ). %%control-driven 
%%% -------------------------------------------
eventhandler(evhtml,ctx,"it.unibo.ctx.Evhtml","usercmd,cmd,alarm").  
%%% -------------------------------------------

