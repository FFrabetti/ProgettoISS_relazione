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

// periodo di loop(), arg di delay
const int LOOP_PERIOD = 500;
// frequenza seriale
const int SERIAL_FREQ = 9600;
// soglia temperatura
const float T_MAX = 30;
// variazione minima di temperatura per scrittura su seriale
const float DELTA_T = 0.5;
// ogni quante iter la temperatura viene comunque inviata
const int ITER_T = 5;
// angoli servomotore
const int SRV_FRONT = 90;
const int SRV_LEFT = 45;
const int SRV_RIGHT = 135;

// NOTA: lo "stato" viene memorizzato all'interno delle var glob
char n = '\0';	// ultimo carattere letto da seriale
int iter = 0;	// nr di iterazioni di loop() % 1024

float tempState = 0;

int ledState = 0; // 0 = off, 1 = on, 2 = blink

Servo myServo;	// "oggetto" servo motore
int servoState = SRV_FRONT;	// angolo servo motore

int motorState = 0; // 0 = halt, 1 = forward, -1 = backward

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

void loop() {
  if(Serial.available())
    n = Serial.read();
  
  // sensori
  handleTemperature();
  handleSonar();
  
  // attuatori
  handleLed();
  handleServo();
  handleMotor();
  
  iter = (++iter) % 1024;
  delay(LOOP_PERIOD);
}

void handleTemperature() {
  int sensorVal = analogRead(TEMP_PIN);
  //conversione della lettura ADC in tensione
  float volt = (sensorVal / 1024.0) * 5.0;
  //conversione tensione in temperatura
  float temp = (volt - .5) * 100;
  
//  Serial.println("TempSensor: " + String(sensorVal) + " volts: " + String(volt) + " degreesC: " + String(temp));
  if(iter%ITER_T == 0 || abs(temp-tempState) >= DELTA_T)
    Serial.println("Temperature: " + String(temp));
  
  digitalWrite(LOWT_LED_PIN, temp < T_MAX ? HIGH : LOW);
  digitalWrite(HIGHT_LED_PIN, temp < T_MAX ? LOW : HIGH);
  
  tempState = temp;
}

void handleSonar() {
  // TODO: read value from SONAR_PIN
  int sonarVal = 10;

  if(iter%10 == 0)
    Serial.println("Sonar: " + String(sonarVal));
}

void handleLed() {
  if(n=='0' || n=='1' || n=='2') {
    ledState = n - '0';
    n = '\0'; // input consumato
	
    // on/off solo al cambiamento di stato del led
    if(ledState < 2)
      digitalWrite(MOV_LED_PIN, ledState==0 ? LOW : HIGH);
  }
  
  if(ledState == 2) // blink
    digitalWrite(MOV_LED_PIN, iter%2==0 ? LOW : HIGH);
}

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

/*
void handleServo() {
  if(n=='a' || n=='d' || n=='f') {
    if(n=='f') {
	  servoState = SRV_FRONT;
	  n = '\0'; // input consumato
	}
	else {
	  servoState = n=='d' ? SRV_RIGHT : SRV_LEFT;
	  n = 'f';
	}
	
	myServo.write(servoState);
  }
}
*/

void handleMotor() {
  if(n=='w' || n=='s' || n=='h') {
    if(n=='h') {
	  motorState = 0;
	  motorStop();
	}
    else {
	  motorState = n=='w' ? 1 : -1;
	  motorStart(motorState);
	}
	
    n = '\0'; // input consumato
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
