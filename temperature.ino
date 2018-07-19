const int sensorPin = A0;
const int blueLed = 2;
const int redLed = 3;
const int greenLed = 4;
const float tMax = 30;
//int greenLedState = 0;
int n = 0;


void setup() {
  Serial.begin(9600);
  pinMode(blueLed,OUTPUT);
  pinMode(redLed,OUTPUT);
  pinMode(greenLed,OUTPUT);

  digitalWrite(blueLed,LOW);
  digitalWrite(redLed,LOW);
  digitalWrite(greenLed,LOW);
}

void loop() {
  handleTemperature();
  handleLed();
  delay(500);
}

void handleTemperature(){
  int sensorVal = analogRead(sensorPin);
  Serial.print("Sensor value:");
  Serial.print(sensorVal);

  //conversione della lettura ADC in tensione
  float voltage=(sensorVal/1024.0)* 5.0;
  Serial.print(", Volts: ");
  Serial.print(voltage);

  //conversione tensione in temperatura
  float temperature = (voltage - .5) * 100;
  Serial.print(", degrees C: ");
  Serial.println(temperature);

  if(temperature < tMax){
    digitalWrite(blueLed,HIGH);
    digitalWrite(redLed,LOW);
  }
  else {
    digitalWrite(blueLed,LOW);
    digitalWrite(redLed,HIGH);
  }
}

void handleLed(){
  if(Serial.available()){
    n = Serial.read() - '0';
    if(n != 0){//blink
//    if((greenLedState % 2) == 0)
      digitalWrite(greenLed,HIGH);
//    else
//     digitalWrite(greenLed,LOW);
//     greenLedState++;
    }
    else{ //no blink
       digitalWrite(greenLed,LOW);
//      greenLedState = 0;
    }
  }
}

