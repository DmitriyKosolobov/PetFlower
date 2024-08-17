#include <DHT.h>

#define MOIS_PIN 1
#define GHT22_PIN 2



DHT dht22(GHT22_PIN, DHT22);

void setup() {
  Serial.begin(9600);
  dht22.begin();
}

void loop() {
  delay(500);

  int moisVal = analogRead(MOIS_PIN);
  float humVal = dht22.readHumidity();
  float temVal = dht22.readTemperature();
  float light = 10;

  if (isnan(humVal) || isnan(temVal)) {
    Serial.println("Ошибка чтения датчика");
    return;
  }

  Serial.printf("{\"moisture\": %d, \"humidity\": %f, \"temprature\": %f, \"light\": %f}\n", moisVal, humVal, temVal, light);
}
