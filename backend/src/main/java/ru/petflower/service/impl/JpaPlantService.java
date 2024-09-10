package ru.petflower.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.petflower.controller.responses.plant.GetAllPlantsResponse;
import ru.petflower.controller.responses.plant.GetPlantResponse;
import ru.petflower.domain.JpaPlantRepository;
import ru.petflower.domain.entity.Plant;
import ru.petflower.exception.CustomException;
import ru.petflower.exception.ErrorType;
import ru.petflower.service.PlantService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JpaPlantService implements PlantService {

    private final JpaPlantRepository jpaPlantRepository;

    @Override
    public GetPlantResponse getPlant(Long plantId) {
       Optional<Plant> optionalPlant = jpaPlantRepository.findById(plantId);
       if(optionalPlant.isEmpty()) {
           throw new CustomException(ErrorType.NOT_FOUND_EXCEPTION, "Растение не найдено");
       } else {
           Plant plant = optionalPlant.get();
           return new GetPlantResponse(
                   plant.getId(),
                   plant.getName(),
                   plant.getMaxLightLux(),
                   plant.getMinLightLux(),
                   plant.getMaxTemp(),
                   plant.getMinTemp(),
                   plant.getMaxEnvHumid(),
                   plant.getMinEnvHumid(),
                   plant.getMaxSoilMoist(),
                   plant.getMinSoilMoist(),
                   plant.getPlantInfo().getInfo()
           );
       }
    }

    @Override
    public GetAllPlantsResponse getAllPlants() {
        List<Plant> plants = jpaPlantRepository.findAll();
        if(plants.isEmpty()) {
            throw new CustomException(ErrorType.NOT_FOUND_EXCEPTION, "Растения не найдены");
        } else {
            List<GetPlantResponse> getPlantResponseList = plants.stream()
                    .map(plant -> new GetPlantResponse(
                            plant.getId(),
                            plant.getName(),
                            plant.getMaxLightLux(),
                            plant.getMinLightLux(),
                            plant.getMaxTemp(),
                            plant.getMinTemp(),
                            plant.getMaxEnvHumid(),
                            plant.getMinEnvHumid(),
                            plant.getMaxSoilMoist(),
                            plant.getMinSoilMoist(),
                            plant.getPlantInfo().getInfo()
                    ))
                    .toList();
            return new GetAllPlantsResponse(getPlantResponseList);
        }
    }
}
