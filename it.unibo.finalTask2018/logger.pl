% EventHandler evhlogger for cmd, prova /*-print*/ {
%	memoCurrentEvent -lastonly for eventlogger;
%	demo dologevent for eventlogger
% };

dologevent :-
	msg(EVID,'event',EMITTER,none,EVPL,NUM),
	assert(logevent(EVID,EVPL)).
	% actorPrintln(loggedev(EVID,EVPL)).
