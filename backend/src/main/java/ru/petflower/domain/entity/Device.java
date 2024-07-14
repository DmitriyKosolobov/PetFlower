package ru.petflower.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "device")
@Getter
@Setter
@NoArgsConstructor
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "device_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserAccount userAccount;

    @ManyToOne
    @JoinColumn(name = "plant_id")
    private Plant plant;

    private Double battery;

    @OneToMany(mappedBy = "device")
    private List<Measure> measures = new ArrayList<>();

    public void addMeasure(Measure measure) {
        this.measures.add(measure);
        measure.setDevice(this);
    }

    public void removeMeasure(Measure measure) {
        this.measures.remove(measure);
        measure.setDevice(null);
    }

}
