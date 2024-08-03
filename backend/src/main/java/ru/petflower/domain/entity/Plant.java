package ru.petflower.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "plant")
@Getter
@Setter
@NoArgsConstructor
public class Plant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plant_id")
    private Long id;

    private String name;

    private String info;

    private Integer temperature;

    private Integer humidity;

    private Integer light;

    private Integer moisture;

    @OneToMany(mappedBy = "plant")
    private List<Device> devices = new ArrayList<>();

    public Plant(String name, String info, Integer temperature,
                 Integer humidity, Integer light, Integer moisture) {
        this.name = name;
        this.info = info;
        this.temperature = temperature;
        this.humidity = humidity;
        this.light = light;
        this.moisture = moisture;
    }

    public void addDevice(Device device) {
        this.devices.add(device);
        device.setPlant(this);
    }

    public void removeDevice(Device device) {
        this.devices.remove(device);
        device.setPlant(null);
    }

}
