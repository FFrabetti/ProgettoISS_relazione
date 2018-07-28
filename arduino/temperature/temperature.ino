#include <Servo.h>

const int SRV_PIN = 9;

const int sensorPin = A0;

const int lowTLed = 2;
const int highTLed = 3;
const int outLed = 4;

const float tMax = 30;
const int period = 500;

char n = '0';
Servo myServo;
int angle = 90;

void setup() {
  Serial.begin(9600);
  pinMode(lowTLed,OUTPUT);
  pinMode(highTLed,OUTPUT);
  pinMode(outLed,OUTPUT);

  digitalWrite(lowTLed,LOW);
  digitalWrite(highTLed,LOW);
  digitalWrite(outLed,LOW);

  myServo.attach(SRV_PIN);
}

void loop() {
  if(Serial.available()){
    n = Serial.read();
  }
  
  handleTemperature();
  handleLed();
  handleServo();
  delay(period);
}

void handleTemperature() {
  int sensorVal = analogRead(sensorPin);
  Serial.print("Sensor: ");
  Serial.print(sensorVal);

  //conversione della lettura ADC in tensione
  float voltage = (sensorVal / 1024.0) * 5.0;
  Serial.print(", Volts: ");
  Serial.print(voltage);

  //conversione tensione in temperatura
  float temperature = (voltage - .5) * 100;
  Serial.print(", degreesC: ");
  Serial.println(temperature);

  if(temperature < tMax){
    digitalWrite(lowTLed,HIGH);
    digitalWrite(highTLed,LOW);
  }
  else {
    digitalWrite(lowTLed,LOW);
    digitalWrite(highTLed,HIGH);
  }
}

void handleLed() {
  int i = n - '0';
  if(i == 1) { // on
    digitalWrite(outLed,HIGH);
  }
  else if(i == 0) { // off
    digitalWrite(outLed,LOW);
  }
}

void handleServo() {
  int i = n - '0';
  if(i == 2) {
    angle = 45;
  }
  else if(i == 3) {
    angle = 135;
  }
  myServo.write(angle);
  if(i==2 || i==3)
    delay(15);
}

