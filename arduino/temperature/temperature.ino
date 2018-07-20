const int sensorPin = A0;

const int lowTLed = 2;
const int highTLed = 3;
const int outLed = 4;

const float tMax = 30;
const int period = 500;

void setup() {
  Serial.begin(9600);
  pinMode(lowTLed,OUTPUT);
  pinMode(highTLed,OUTPUT);
  pinMode(outLed,OUTPUT);

  digitalWrite(lowTLed,LOW);
  digitalWrite(highTLed,LOW);
  digitalWrite(outLed,LOW);
}

void loop() {
  handleTemperature();
  handleLed();
  delay(period);
}

void handleTemperature(){
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

void handleLed(){
  int n;
  if(Serial.available()){
    n = Serial.read() - '0';
    if(n != 0){ // on
      digitalWrite(outLed,HIGH);
    }
    else{ // off
       digitalWrite(outLed,LOW);
    }
  }
}
