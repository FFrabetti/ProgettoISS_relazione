% server address and port
server(localhost,6666).

% servo
map(a(_),a). % af ???
map(d(_),d). % df ???

% motor
map(w(_),w).
map(s(_),s).
map(h(_),h).

% led
map(off,0).
map(on,1).
map(blink,2).

mapCmd(IN,OUT) :- receivedCmd(IN), map(IN,OUT).
