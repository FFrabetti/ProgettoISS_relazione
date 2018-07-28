#!/usr/bin/env python3

import serial
asd = serial.Serial('/dev/ttyACM0',9600)

while True:
	valore=input("Comando: ")
	asd.write(valore.encode('utf-8'))
	print("Inviato " + valore)



