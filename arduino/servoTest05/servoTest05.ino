#include <Servo.h>

int const SRV_PIN = 9;
int const MOTOR_PIN = 8;

Servo myServo;

void setup() {
  myServo.attach(SRV_PIN);
  pinMode(MOTOR_PIN, OUTPUT);
  
  Serial.begin(9600);
}

void loop() {
  int n;
  int angle = 0;
  if(Serial.available()){
    n = Serial.read() - '0';
    angle = map(n, 0, 9, 0, 179);
    myServo.write(angle);
    delay(15);

    if(n==0)
      digitalWrite(MOTOR_PIN, LOW);
    else
      digitalWrite(MOTOR_PIN, HIGH);
  }
}
