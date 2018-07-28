#!/usr/bin/env python3

import serial
import sys
import time

def serialwr(s, val):
	s.write(val.encode())


if len(sys.argv)!=2:
    print("Expected one arg: CMD")
    quit()


try:
    cmd = sys.argv[1]
    
    if cmd!="left" and cmd!="right":
        raise ValueError
    
except ValueError:
    print("Invalid argument: " + sys.argv[1])
    quit()

asd = serial.Serial('/dev/ttyACM0',9600)
time.sleep(3)

if cmd=="left":
	serialwr(asd,'2') # asd.write('1')
elif cmd=="right":
	serialwr(asd,'3') # asd.write('0')

