% server address and port
%server(localhost,6666).
server(addr(10,0,3,14),6666).

% servo
map(a(_),aaaf). % a
map(d(_),dddf). % d

% motor
map(w(_),w).
map(s(_),s).
map(h(_),h).

% led
map(off,0).
map(on,1).
map(blink,2).

mapCmd(IN,OUT) :- receivedCmd(IN), map(IN,OUT).
