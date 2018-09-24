:: start temperature server (GUI)
:: (in alternativa al server python sul raspberry)
:: RICORDA di inserire il giusto indirizzo in temperatureAgent.pl
start cmd /k java -jar ThermoServer.jar

:: start mock hue lamp
start cmd /k java -jar HueLampMockServer.jar

timeout 30
cd distr3

:: start CRS appl
:: browser: localhost:8080
start cmd /k java -jar it.unibo.ctxAppl-1.0.jar

timeout 30

:: start virtual robot node
:: browser: localhost:8090
start cmd /k java -jar it.unibo.ctxVirtualRobotNode-1.0.jar

timeout 30

:: start frontend server
:: browser: localhost:3000
start cmd /k java -jar it.unibo.ctxFrontendActivator-1.0.jar
