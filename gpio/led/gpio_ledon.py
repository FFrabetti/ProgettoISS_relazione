#!/usr/bin/env python3

import sys
#import time
from gpiozero import LED
from signal import pause

#pin = 12

if len(sys.argv)!=2:
    print("Expected one arg: PIN")
    quit()

try:
    pin = int(sys.argv[1])
    
    if pin<1 or pin>26:
        raise ValueError
    
except ValueError:
    print("Invalid argument: " + sys.argv[1])
    quit()

ledpin = LED(pin)

ledpin.on()

pause()
