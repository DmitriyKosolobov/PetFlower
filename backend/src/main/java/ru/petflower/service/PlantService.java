package ru.petflower.service;

import ru.petflower.domain.entity.Pet;
import ru.petflower.domain.entity.Plant;

import java.util.Optional;

public interface PlantService {
    void addPlant();
    void changePlant();
    void deletePlant();
    Optional<Plant> getPlant();
    Optional<Plant> getPlantInfo();
}
