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

    @OneToMany(mappedBy = "plant")
    private List<Device> devices = new ArrayList<>();

    public void addDevice(Device device) {
        this.devices.add(device);
        device.setPlant(this);
    }

    public void removeDevice(Device device) {
        this.devices.remove(device);
        device.setPlant(null);
    }

}
