package ru.petflower.service.impl;

import org.springframework.stereotype.Service;
import ru.petflower.domain.entity.Plant;
import ru.petflower.service.PlantService;

import java.util.Optional;

@Service
public class JpaPlantService implements PlantService {
    @Override
    public void addPlant() {

    }

    @Override
    public void changePlant() {

    }

    @Override
    public void deletePlant() {

    }

    @Override
    public Optional<Plant> getPlant() {
        return Optional.empty();
    }

    @Override
    public Optional<Plant> getPlantInfo() {
        return Optional.empty();
    }
}
