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

    @Column(name = "device_key")
    private String key;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserAccount userAccount;

    @OneToMany(mappedBy = "device")
    private List<Measure> measures = new ArrayList<>();

    @OneToOne(mappedBy = "device")
    private Pet pet;

    public Device(String key) {
        this.key = key;
    }

    public void addMeasure(Measure measure) {
        this.measures.add(measure);
        measure.setDevice(this);
    }

    public void removeMeasure(Measure measure) {
        this.measures.remove(measure);
        measure.setDevice(null);
    }

    public void addPet(Pet pet) {
        this.pet = pet;
        pet.setDevice(this);
    }

    public void removePet(Pet pet) {
        this.pet = null;
        pet.setDevice(null);
    }

}
