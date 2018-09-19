% server address and port
server(localhost,6666).
%server(addr(10,0,3,14),6666).

% servo
map(a(_),'t0 3000 90'). % a aaaf
map(d(_),'t155 3000 90'). % d dddf

% motor
map(w(_),w).
map(s(_),s).
map(h(_),h).

% led
map(off,l0).
map(on,l1).
map(blink,l2).

mapCmd(IN,OUT) :- receivedCmd(IN), map(IN,OUT).
