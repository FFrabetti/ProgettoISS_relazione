#!/usr/bin/env python3

import sys
import socket

HOST = '10.0.3.14' # socket.INADDR_ANY
PORT = 6666

if len(sys.argv)==3:
	HOST = sys.argv[1]
	PORT = int(sys.argv[2])


print("Creating server socket temp")
s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)

try:
	s.bind((HOST,PORT))

	while True:
		print(s.recvfrom(65565))

finally:
	s.close()

