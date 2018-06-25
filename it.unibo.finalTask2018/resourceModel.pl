/*
===============================================================
resourceModel.pl
===============================================================
*/
model( type(actuator, led), name(l1), value(noblink) ).
model( type(sensor, temperature), name(t1),   value(25)  ).
model( type(sensor, clock), name(c1),   value(h(7,0))  ).

%%Modello del robot
model( type(robots, robot), name(r1), value(h(0)) ).

getModelItem( TYPE, CATEG, NAME, VALUE ) :-
		model( type(TYPE, CATEG), name(NAME), value(VALUE) ).
changeModelItem( CATEG, NAME, VALUE ) :-
 		replaceRule( 
			model( type(TYPE, CATEG), name(NAME), value(_) ),  
			model( type(TYPE, CATEG), name(NAME), value(VALUE) ) 		
		),!,
		%%output( changedModelAction(CATEG, NAME, VALUE) ),
		( changedModelAction(CATEG, NAME, VALUE) %%to be defined by the appl designer
		  ; true ).		%%to avoid the failure if no changedModelAction is defined
		
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



/*  		
	changedModelAction( temperature, t1, V  ):-
 		    eval( ge, V , 30 ), !,  
 		    changemodelitem( leds, led1, on).		     
 	changedModelAction( temperature, t1, V  ):-	 
 			changemodelitem( leds, led1, off).     			 			
 	changedModelAction( leds, led1, V  ):-
 			emitevent( ctrlEvent, ctrlEvent( leds, led1, V) ).
 */
