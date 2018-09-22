delayEmission(10000).
wait(10000).

waitLight(T) :- light(_), wait(T).

cmd(w(0)).
cmd(h(0)).
cmd(s(0)).
cmd(h(0)).
cmd(a(0)).
cmd(w(0)).
cmd(h(0)).
cmd(d(0)).

light(on).
light(off).
light(blink).
