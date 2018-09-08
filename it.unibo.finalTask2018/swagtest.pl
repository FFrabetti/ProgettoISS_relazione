	addState(S) :- 
		findall(X,logstate(X,Y),LIST),
		length(LIST,N),
		assert(logstate(S,N)).
 
 	stateHistory(LIST) :- stateHistory(LIST,0).
 	stateHistory([],_).
 	stateHistory([H|T],N) :- 
 			logstate(H,N),
 			N2 is N+1,
 			stateHistory(T,N2).