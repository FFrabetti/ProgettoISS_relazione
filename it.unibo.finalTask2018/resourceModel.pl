/*
===============================================================
resourceModel.pl
===============================================================
*/
model( type(actuator, led), name(l1), value(noblink) ).
model( type(sensor, temperature), name(t1),   value(20)  ).
model( type(sensor, clock), name(c1),   value(h(8,0))  ).

%%Modello del robot
model( type(robots, robot), name(r1), value(h(0)) ).

getModelItem( TYPE, CATEG, NAME, VALUE ) :-
		model( type(TYPE, CATEG), name(NAME), value(VALUE) ).

% se devo sovrascrivere lo stesso valore, non faccio niente
changeModelItem( CATEG, NAME, VALUE ) :- getModelItem( _, CATEG, NAME, VALUE ), !.

changeModelItem( CATEG, NAME, VALUE ) :-
 		replaceRule( 
			model( type(TYPE, CATEG), name(NAME), value(_) ),  
			model( type(TYPE, CATEG), name(NAME), value(VALUE) ) 		
		),!,
		%%output( changedModelAction(CATEG, NAME, VALUE) ),
		( changedModelAction(CATEG, NAME, VALUE) %%to be defined by the appl designer
		  ; true ).		%%to avoid the failure if no changedModelAction is defined



non(P) :- call(P), !, fail.
non(P).
		
eval( ge, X, X ) :- !. 
eval( ge, X, V ) :- eval( gt, X , V ) .

eval(le,X,X) :- !.
eval(le,X,V) :- eval( lt, X , V).



emitevent( EVID, EVCONTENT ) :- 
	actorobj( Actor ), 
	%%output( emit( Actor, EVID, EVCONTENT ) ),
	Actor <- emit( EVID, EVCONTENT ).
%%%  initialize
initResourceTheory :- output("initializing the initResourceTheory ...").
:- initialization(initResourceTheory).