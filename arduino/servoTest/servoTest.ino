#include <Servo.h>

const int LED_PIN = 4;
const int SRV_PIN = 9;

const int BUFF_LEN = 64;

Servo myServo;

int angle = 0;
int finalAngle = 0;
char buffer[BUFF_LEN];
unsigned long timeout = 0;
unsigned long endTurn = 0;

void setup() {
	pinMode(LED_PIN, OUTPUT);
	digitalWrite(LED_PIN, LOW);
	
	myServo.attach(SRV_PIN);
	Serial.begin(9600);
}

// Serial input:
// 		sANGLE
// 		lDURATION
// 		tANGLE DURATION FINAL_ANGLE
void loop() {
	handleServo();
	handleLed();
	handleTurn();
	delay(200);
}

void readEOL() {
	int n = Serial.readBytesUntil('\n', buffer, BUFF_LEN);
	if(n > 0)
		Serial.println("Letti " + String(n) + " bytes: [" + buffer + "]");
}

void setAngle(int a) {
	angle = constrain(a, 0, 180);
	myServo.write(angle);
	Serial.println("Angle: " + String(angle));
}

void handleServo() {
	int a;

	if(Serial.available() && Serial.peek()=='s') {
		Serial.read(); // 's'
		a = Serial.parseInt();
		setAngle(a);
		
		readEOL();
    }
}

void handleLed() {
	int t;

	if(Serial.available() && Serial.peek()=='l') {
		Serial.read(); // 'l'
		t = Serial.parseInt();

		timeout = millis() + t;
		Serial.println("Led timeout: " + String(t));
		readEOL();
    }
	
	digitalWrite(LED_PIN, millis() < timeout ? HIGH : LOW);
}

void handleTurn() {
	int a, t;

	if(Serial.available() && Serial.peek()=='t') {
		Serial.read(); // 't'
		a = Serial.parseInt();
		t = Serial.parseInt();
		finalAngle = Serial.parseInt(); // se non presente 0
		
		setAngle(a);
		endTurn = millis() + t;
		Serial.println("endTurn: " + String(t));
		
		readEOL();
    }
	
	if(endTurn != 0 && millis() > endTurn) {
		endTurn = 0;
		Serial.println("endTurn elapsed");
		setAngle(finalAngle);
	}
}
