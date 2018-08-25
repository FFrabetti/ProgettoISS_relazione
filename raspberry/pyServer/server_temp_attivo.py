#!/usr/bin/env python3
# args: HOST ROBOT_PORT TEMP_PORT

import sys
import socket
# import serial
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

################################
class ServerThread(Thread):
	def __init__(self, port, name, dict, asd):
		Thread.__init__(self)
		self.port = port
		self.name = name
		self.dict = dict
		self.asd = asd
	def run(self):
		print("Thread " + self.name + ": started")
		s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
		s.bind((HOST, self.port))
		s.listen(1) # dim della coda di richieste
		print("Thread " + self.name + ": waiting for a connection on port", self.port)
		conn, addr = s.accept()
		print("Thread " + self.name + ": connection address", addr)
		# aggiungo la connessione al dizionario
		self.dict[self.name] = conn

		if(self.name==ROBOT_KEY):
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
					# Write the bytes data to the port. This should be of type bytes (or compatible such as bytearray or memoryview).
					# Unicode strings must be encoded (e.g. 'hello'.encode('utf-8').
					asd.write(line)
			finally:
				conn.close()
				del(self.dict[self.name])
				
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
		return 'Sonar: 44' if random.randint(0, 1)==0 else 'Temp: 20'
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
#  __init__(port=None, baudrate=9600, bytesize=EIGHTBITS, parity=PARITY_NONE, stopbits=STOPBITS_ONE, timeout=None, xonxoff=False, rtscts=False, write_timeout=None, dsrdtr=False, inter_byte_timeout=None) 	
#asd = serial.Serial(SERIAL_PORT, SERIAL_BAUDRATE)
asd = MockSerial() # test senza arduino

serverRobot = ServerThread(ROBOT_PORT, ROBOT_KEY, sockDict, asd)
serverTemp = ServerThread(TEMP_PORT, TEMP_KEY, sockDict, asd)

serverRobot.start()
serverTemp.start()

try:
	while True:
		if(asd.in_waiting > 0): # changed to property from inWaiting(). Get the number of bytes in the input buffer
			data = asd.readline()
			print("Serial:", data)
			list = str(data).split()
			type = list[0]
			value = list[1]
	##		print(type)
	##		print(value)

			# dispatching tra le (due) socket
			dest = ROBOT_KEY if type=="Sonar:" else TEMP_KEY
			if dest in sockDict:
				sockDict[dest].sendall((value+"\n").encode())
				print("sending", value, "to", dest,"client")
finally:
	for s in sockDict:
		s.close()
