#!/usr/bin/env python3
# args: HOST ROBOT_PORT TEMP_PORT

import sys
import socket
import serial
import time
import random		# for testing
from threading import Thread

# HOST = '10.0.3.14' # socket.INADDR_ANY
HOST = '' # the socket is reachable by any address the machine happens to have
ROBOT_PORT = 6666
TEMP_PORT = 6667
ROBOT_KEY = 'robot'
TEMP_KEY = 'temp'
BUFFER_SIZE = 1024
SERIAL_PORT = '/dev/ttyACM0'
SERIAL_BAUDRATE = 9600

# temperatura corrente aggiornata man mano che si ricevono nuovi dati dal sensore
# var condivisa tra i thread
currTemp = '0'

def socket_sendmsg(sockDict, key, value):
	if(key in sockDict):
		sockDict[key].sendall((value+"\n").encode())
		print("sending", value, "to", key)

################################
class ServerThread(Thread):
	def __init__(self, port, name, sockDict, asd):
		Thread.__init__(self)
		self.port = port
		self.name = name
		self.sockDict = sockDict
		self.asd = asd
	def run(self):
		print("Thread " + self.name + ": started")
		s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
		s.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
		s.bind((HOST, self.port))
		s.listen(1) # dim della coda di richieste
		print("Thread " + self.name + ": waiting for a connection on port", self.port)
		conn, addr = s.accept()
		print("Thread " + self.name + ": connection address", addr)
		# aggiungo la connessione al dizionario
		self.sockDict[self.name] = conn

		try:
			# https://docs.python.org/3.1/library/stdtypes.html#sequence-types-str-bytes-bytearray-list-tuple-range
			# https://docs.python.org/3.1/library/stdtypes.html#typesseq-mutable
			buffer = bytearray() # mutable
			while True:
				# buffer = bytes() # https://docs.python.org/3.1/library/functions.html#bytes
				while not ord('\n') in buffer:
					data = conn.recv(BUFFER_SIZE) # 1 ??? un byte alla volta fino a '\n'?
					if not data: break
					buffer += data
					
				if not buffer: break
				delim = buffer.index(ord('\n'))
				line = buffer[:delim] # fine linea escluso
				del buffer[:delim+1]
				print("Thread " + self.name + ": received", line.decode())
				
				# inoltro dei comandi per il robot sulla seriale
				if(self.name==ROBOT_KEY):
					# Write the bytes data to the port. This should be of type bytes (or compatible such as bytearray or memoryview).
					# Unicode strings must be encoded (e.g. 'hello'.encode('utf-8').
					asd.write(line)
				
				# temperatura corrente inviata solo in seguito di una richiesta
				if(self.name==TEMP_KEY):
					socket_sendmsg(self.sockDict, TEMP_KEY, currTemp)
		finally:
			conn.close()
			del(self.sockDict[self.name])
				
		print ("Thread " + self.name + ": ended")
		
# mock della conn seriale (test senza arduino)
class MockSerial:
	@property
	def in_waiting(self):
		return self.inWaiting()
	def inWaiting(self):
		time.sleep(3)
		return 1
	def readline(self):
		i = random.randint(0, 30)
		return 'Sonar: '+str(i) if i%2==0 else 'Temperature: '+str(i)
	def write(self, s):
		print(s.decode())
################################


# argomenti da linea di comando
if len(sys.argv)==4:
	HOST = sys.argv[1]
	ROBOT_PORT = int(sys.argv[2])
	TEMP_PORT = int(sys.argv[3])

# dizionario per le connessioni	attive (oggetto condiviso tra i thread)
sockDict = dict()

# https://pythonhosted.org/pyserial/pyserial_api.html#classes
asd = serial.Serial(SERIAL_PORT, SERIAL_BAUDRATE)
#asd = MockSerial() # test senza arduino

serverRobot = ServerThread(ROBOT_PORT, ROBOT_KEY, sockDict, asd)
serverTemp = ServerThread(TEMP_PORT, TEMP_KEY, sockDict, asd)

serverRobot.start()
serverTemp.start()

try:
	while True:
		if(asd.inWaiting() > 0): # changed to property in_waiting from inWaiting(). Get the number of bytes in the input buffer
			data = asd.readline().decode()
			print("Serial:", data)
			list = data.split()
			if(len(list)!=2): # no letture spurie
#				print("len(list) = ", len(list))
				continue
			type = list[0]
			value = list[1]
#			print("Type:",type," - Value:",value)
	
			# appena il sonar rileva qualcosa, lo notifico al client
			if(type=="Sonar:"):
				socket_sendmsg(sockDict, ROBOT_KEY, value)
#				print("new sonar:", value)
			
			# il sensore di temperatura invece e' passivo: invia dati solo su richiesta
			if(type=="Temperature:"):
				currTemp = value
#				print("new temp:", currTemp)
finally:
	for s in sockDict:
		s.close()
