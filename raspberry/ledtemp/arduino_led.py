#!/usr/bin/env python3

import serial
import sys
import time

def serialwr(s, val):
	s.write(val.encode())


cmd = "off"

if len(sys.argv)!=2:
    print("Expected one arg: CMD")
    quit()

try:
    cmd = sys.argv[1]
    
    if cmd!="on" and cmd!="off" and cmd!="blink":
        raise ValueError
    
except ValueError:
    print("Invalid argument: " + sys.argv[1])
    quit()

asd = serial.Serial('/dev/ttyACM0',9600)
time.sleep(3)

if cmd=="on":
	serialwr(asd,'1') # asd.write('1')
elif cmd=="off":
	serialwr(asd,'0') # asd.write('0')
else:
	while True:
		serialwr(asd,'1') # asd.write('1')
		time.sleep(0.5)
		serialwr(asd,'0') # asd.write('0')
		time.sleep(0.5)

