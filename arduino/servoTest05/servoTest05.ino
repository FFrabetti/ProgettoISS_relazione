#include <Servo.h>

int const SRV_PIN = 9;
int const MOTOR_PIN = 8;
int const FORW_PIN = 7;
int const RETRO_PIN = 6;
int const LED_PIN = 12;

Servo myServo;

void setup() {
	myServo.attach(SRV_PIN);
	pinMode(MOTOR_PIN, OUTPUT);
	pinMode(FORW_PIN, OUTPUT);
	pinMode(RETRO_PIN, OUTPUT);
	pinMode(LED_PIN, OUTPUT);

	Serial.begin(9600);
}

void loop() {
	delay(200);
	// int n;
	// int angle = 0;
	
	if(Serial.available()) {
    // n = Serial.read() - '0';
    // angle = map(n, 0, 9, 0, 179);
    // myServo.write(angle);
	
		char n = Serial.read();
		if(n=='w')
			motorForward();
		else if(n=='s')
			motorBackward();
		else if(n=='h')
			motorStop();
	}
}

void motorForward() {
	motorStart(1);
}

void motorBackward() {
	motorStart(-1);
}

void motorStart(int dir) {
	digitalWrite(MOTOR_PIN, HIGH);
	digitalWrite(LED_PIN, HIGH);
	digitalWrite(FORW_PIN, dir>0 ? HIGH : LOW);
	digitalWrite(RETRO_PIN, dir>0 ? LOW : HIGH);
}

void motorStop() {
	digitalWrite(MOTOR_PIN, LOW);
	digitalWrite(LED_PIN, LOW);
	digitalWrite(FORW_PIN, LOW);
	digitalWrite(RETRO_PIN, LOW);
}
