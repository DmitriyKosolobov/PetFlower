package ru.petflower.service;

import ru.petflower.controller.responses.plant.GetAllPlantsResponse;
import ru.petflower.controller.responses.plant.GetPlantResponse;

public interface PlantService {
//    void addPlant();
//    void changePlant();
//    void deletePlant();
    GetPlantResponse getPlant(Long plantId);
    GetAllPlantsResponse getAllPlants();
}
