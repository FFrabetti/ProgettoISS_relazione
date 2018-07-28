#!/usr/bin/env python3

import serial
asd = serial.Serial('/dev/ttyACM0',9600)
while True:
	if(asd.inWaiting()>0):
		data = asd.readline()
		print(data)


