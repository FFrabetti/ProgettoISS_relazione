#!/usr/bin/env python3

import sys
import socket
import serial
import time

HOST = '10.0.3.14' # socket.INADDR_ANY
PORT = 6666

if len(sys.argv)==3:
	HOST = sys.argv[1]
	PORT = int(sys.argv[2])

print("Creating socket temp")
s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)

try:
	s.bind((HOST,PORT))

	asd = serial.Serial('/dev/ttyACM0',9600)
	while True:
#		(msg,(h,p)) = s.recvfrom(65565)
		asd.flushInput()
#		asd.reset_input_buffer()
		while(asd.inWaiting()==0):
			time.sleep(0.5)

		if(asd.inWaiting()>0):
			data = asd.readline()
			print(data)
			list = str(data).split()
			data = list[len(list)-1][:-5]
#			print(data)
			data = data.encode()
			s.sendto(data, ('10.0.3.3',7777))

finally:
	s.close()

