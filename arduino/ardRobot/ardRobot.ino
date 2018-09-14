#include <Servo.h>

// ---------------- PIN ----------------
// SENSORI
// sensore temperatura (analog PIN)
const int TEMP_PIN = A0;
// sonar (TODO)
// const int SONAR_PIN = A1;

// ATTUATORI
// 2 led temperatura (sotto/sopra la soglia)
const int LOWT_LED_PIN = 2;
const int HIGHT_LED_PIN = 3;
// led di movimento (blink)
const int MOV_LED_PIN = 4;
// servo motore
const int SRV_PIN = 9;
// motore elettrico
const int MOTOR_PIN = 8;
const int FORW_PIN = 7;
const int RETRO_PIN = 6;
const int MOTOR_LED_PIN = 12; //  debug
// ---------------- PIN ----------------

// periodo di loop(), arg di delay (ms)
const int LOOP_PERIOD = 500;
// frequenza seriale
const int SERIAL_FREQ = 9600;
// frequenza di blink
const int BLINK_FREQ = 500;
// soglia temperatura
const float T_MAX = 30;
// variazione minima di temperatura per scrittura su seriale
const float DELTA_T = 0.5;
// ogni quanti ms la temperatura viene comunque inviata
const int SEND_T = 1000;
// angoli servomotore
const int SRV_FRONT = 90;
const int SRV_LEFT = 45;
const int SRV_RIGHT = 135;
// buffer per lettura caratteri spuri (fino a \n)
const int BUFF_LEN = 64;
char buffer[BUFF_LEN];

// NOTA: lo "stato" viene memorizzato all'interno delle var glob
char firstChar = '\0';	// primo carattere letto da seriale

float tempState = 0;
int ledCmd = 0; // 0 = off, 1 = on, 2 = blink
int ledState = LOW; // LOW / HIGH
Servo myServo;	// "oggetto" servo motore
int servoAngle = SRV_FRONT;	// angolo servo motore
int finalAngle = SRV_FRONT; // angolo dopo una svolta
int motorState = 0; // 0 = halt, 1 = forward, -1 = backward

unsigned long lastTempSend = 0;
unsigned long lastSonarSend = 0; // debug
unsigned long lastBlink = 0;
unsigned long endTurn = 0;

void setPinOutput(int pin) {
  pinMode(pin, OUTPUT);
  digitalWrite(pin, LOW);
}

void setup() {
  setPinOutput(LOWT_LED_PIN);
  setPinOutput(HIGHT_LED_PIN);
  setPinOutput(MOV_LED_PIN);
  setPinOutput(MOTOR_PIN);
  setPinOutput(FORW_PIN);
  setPinOutput(RETRO_PIN);
  setPinOutput(MOTOR_LED_PIN); // debug
  
  myServo.attach(SRV_PIN);
  
  Serial.begin(SERIAL_FREQ);
}

void readEOL() {
	int n = Serial.readBytesUntil('\n', buffer, BUFF_LEN);
//	if(n > 0)
//		Serial.println("Letti " + String(n) + " bytes: [" + buffer + "]");
}

void loop() {
  firstChar = Serial.available() ? Serial.read() : '\0';
  
  // sensori
  handleTemperature();
  handleSonar();
  
  // attuatori
  handleLed();
  handleServo();
  handleMotor();
  
  if(firstChar)
	  readEOL();
  
  delay(LOOP_PERIOD);
}

void handleTemperature() {
  int sensorVal = analogRead(TEMP_PIN);
  //conversione della lettura ADC in tensione
  float volt = (sensorVal / 1024.0) * 5.0;
  //conversione tensione in temperatura
  float temp = (volt - .5) * 100;
  
//  Serial.println("TempSensor: " + String(sensorVal) + " volts: " + String(volt) + " degreesC: " + String(temp));
  if(millis()-lastTempSend > SEND_T || abs(temp-tempState) >= DELTA_T) {
    Serial.println("Temperature: " + String(temp));
	lastTempSend = millis();
  }
  
  digitalWrite(LOWT_LED_PIN, temp < T_MAX ? HIGH : LOW);
  digitalWrite(HIGHT_LED_PIN, temp < T_MAX ? LOW : HIGH);
  
  tempState = temp;
}

void handleSonar() {
  // TODO: read value from SONAR_PIN
  int sonarVal = millis() % 16;
  
  // debug: send a 'random' value every 10s
  if(millis()-lastSonarSend > 10000) {
    Serial.println("Sonar: " + String(sonarVal));
	lastSonarSend = millis();
  }
}

void handleLed() {
  if(firstChar=='l') {
    ledCmd = Serial.parseInt();
	
    // on/off solo al cambiamento di stato del led
    if(ledCmd < 2) {
      ledState = ledCmd==0 ? LOW : HIGH;
      digitalWrite(MOV_LED_PIN, ledState);
	}
  }
  
  // blink
  if(ledCmd == 2 && millis()-lastBlink > BLINK_FREQ) {
	ledState = ledState==LOW ? HIGH : LOW;
    digitalWrite(MOV_LED_PIN, ledState);
	lastBlink = millis();
  }
}

void handleServo() {
	int angle, duration;

	if(firstChar=='t') {
		angle = Serial.parseInt();
		duration = Serial.parseInt();
		finalAngle = Serial.parseInt(); // 0 se non presente
		
		setAngle(angle);
		endTurn = millis() + duration;
//		Serial.println("endTurn: " + String(duration) + " ms");
    }
	
	if(endTurn != 0 && millis() > endTurn) {
		endTurn = 0;
//		Serial.println("endTurn elapsed");
		setAngle(finalAngle);
	}
}

/*
void handleServo() {
  if(n=='a' || n=='d' || n=='f') {
    if(n=='f')
      servoState = SRV_FRONT;
    else
      servoState = n=='d' ? SRV_RIGHT : SRV_LEFT;

    myServo.write(servoState);
    n = '\0'; // input consumato
  }
}
*/

void handleMotor() {
  if(firstChar=='h') {
    motorState = 0;
    motorStop();
  }
  else if(firstChar=='w' || firstChar=='s') {
    motorState = firstChar=='w' ? 1 : -1;
    motorStart(motorState);
  }
}

void motorStart(int dir) {
  digitalWrite(FORW_PIN, dir>0 ? HIGH : LOW);
  digitalWrite(RETRO_PIN, dir>0 ? LOW : HIGH);

  // enable
  digitalWrite(MOTOR_PIN, HIGH);
  digitalWrite(MOTOR_LED_PIN, HIGH); // debug
}

void motorStop() {
  // disable
  digitalWrite(MOTOR_PIN, LOW);
  digitalWrite(MOTOR_LED_PIN, LOW); // debug
  
  digitalWrite(FORW_PIN, LOW);
  digitalWrite(RETRO_PIN, LOW);
}

void setAngle(int a) {
	servoAngle = constrain(a, 0, 180);
	myServo.write(servoAngle);
//	Serial.println("Servo angle: " + String(servoAngle));
}
