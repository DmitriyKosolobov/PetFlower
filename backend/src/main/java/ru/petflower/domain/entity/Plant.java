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

    @Column(name = "max_light_lux")
    private Integer maxLightLux;

    @Column(name = "min_light_lux")
    private Integer minLightLux;

    @Column(name = "max_temp")
    private Integer maxTemp;

    @Column(name = "min_temp")
    private Integer minTemp;

    @Column(name = "max_env_humid")
    private Integer maxEnvHumid;

    @Column(name = "min_env_humid")
    private Integer minEnvHumid;

    @Column(name = "max_soil_moist")
    private Integer maxSoilMoist;

    @Column(name = "min_soil_moist")
    private Integer minSoilMoist;

    @OneToMany(mappedBy = "plant")
    private List<Pet> pets = new ArrayList<>();

    @OneToOne(mappedBy = "plant")
    private PlantInfo plantInfo;

    public Plant(String name,
                 Integer maxLightLux, Integer minLightLux,
                 Integer maxTemp, Integer minTemp,
                 Integer maxEnvHumid, Integer minEnvHumid,
                 Integer maxSoilMoist, Integer minSoilMoist) {
        this.name = name;
        this.maxLightLux = maxLightLux;
        this.minLightLux = minLightLux;
        this.maxTemp = maxTemp;
        this.minTemp = minTemp;
        this.maxEnvHumid = maxEnvHumid;
        this.minEnvHumid = minEnvHumid;
        this.maxSoilMoist = maxSoilMoist;
        this.minSoilMoist = minSoilMoist;
    }

    public void addPet(Pet pet) {
        this.pets.add(pet);
        pet.setPlant(this);
    }

    public void removePet(Pet pet) {
        this.pets.remove(pet);
        pet.setPlant(null);
    }

    public void addPlantInfo(PlantInfo plantInfo) {
        this.plantInfo = plantInfo;
        plantInfo.setPlant(this);
    }

    public void removePlantInfo(PlantInfo plantInfo) {
        this.plantInfo = null;
        plantInfo.setPlant(null);
    }
}
