% cleaningRobotSystem.qa: QActor temperatureagent configuration file

% server address and port
server(localhost,6667).
%server(addr(10,0,3,14),6667).

% (millis)
requestPeriod(5000).

% payload for temperature requests
requestMsg(tempRequest).
