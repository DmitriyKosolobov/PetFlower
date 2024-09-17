package ru.petflower.util;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.petflower.controller.requests.device.AddMeasureRequest;

@Component
public class MeasuresConvertor {
    @Value("${measures.battery.max}")
    private Integer batteryMaximum;
    @Value("${measures.battery.min}")
    private Integer batteryMinimum;
    @Value("${measures.moisture.max}")
    private Integer moistureMaximum;
    @Value("${measures.moisture.min}")
    private Integer moistureMinimum;

    public AddMeasureRequest convertMeasures(AddMeasureRequest measures) {

        double battery;
        if (measures.battery() > batteryMaximum) {
            battery = 100.0;
        } else if (measures.battery() < batteryMinimum) {
            battery = 0.0;
        } else {
            battery = (measures.battery() - batteryMinimum) * 100.0 / (batteryMaximum - batteryMinimum);
        }

        int moisture;
        if (measures.moisture() < moistureMaximum) {
            moisture = 100;
        } else if (measures.moisture() > moistureMinimum) {
            moisture = 0;
        } else {
            moisture = 100 - (measures.moisture() - moistureMaximum) * 100 / (moistureMinimum - moistureMaximum);
        }

        return new AddMeasureRequest(
                measures.temperature(),
                measures.humidity(),
                measures.light(),
                moisture,
                battery
        );
    }
}
