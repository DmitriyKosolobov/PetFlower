package ru.petflower.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.petflower.controller.dto.AddMeasureRequest;

import java.time.OffsetDateTime;

@Entity
@Table(name = "measure")
@Getter
@Setter
@NoArgsConstructor
public class Measure {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "measure_id")
    private Long id;

    private OffsetDateTime checkTime = OffsetDateTime.now();

    @ManyToOne
    @JoinColumn(name = "device_id")
    private Device device;

    private Integer temp;

    @Column(name = "env_humid")
    private Integer envHumid;

    @Column(name = "light_lux")
    private Integer lightLux;

    @Column(name = "soil_moist")
    private Integer soilMoist;

    @Column(name = "battery_level")
    private Double batteryLevel;

    public Measure(AddMeasureRequest addMeasureRequest){
        this.temp = addMeasureRequest.temperature();
        this.envHumid = addMeasureRequest.humidity();
        this.lightLux = addMeasureRequest.light();
        this.soilMoist = addMeasureRequest.moisture();
        this.batteryLevel = addMeasureRequest.battery();
    }
}
