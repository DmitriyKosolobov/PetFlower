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

    private Integer temperature;

    private Integer humidity;

    private Integer light;

    private Integer moisture;

    public Measure(AddMeasureRequest addMeasureRequest){
        this.temperature = addMeasureRequest.temperature();
        this.humidity = addMeasureRequest.humidity();
        this.light = addMeasureRequest.light();
        this.moisture = addMeasureRequest.moisture();
    }
}
