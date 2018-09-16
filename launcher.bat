:: start temperature server (GUI)
:: (in alternativa al server python sul raspberry)
:: RICORDA di inserire il giusto indirizzo in temperatureAgent.pl
start cmd /k java -jar ThermoServer.jar

:: start mock hue lamp
start cmd /k java -jar HueLampMockServer.jar

:: start CRS appl
:: DOTO...
