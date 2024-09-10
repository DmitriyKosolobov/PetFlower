package ru.petflower.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.petflower.controller.responses.plant.GetAllPlantsResponse;
import ru.petflower.controller.responses.plant.GetPlantResponse;
import ru.petflower.service.PlantService;

@RestController
@RequiredArgsConstructor
public class PlantController {

	public static final String API_PREFIX = "plants";
	public static final String GET_PLANT = API_PREFIX + "/{plant_id}";
	public static final String GET_All_PLANTS = API_PREFIX;

	private final PlantService plantService;

	@GetMapping(GET_PLANT)
	public ResponseEntity<GetPlantResponse> getPlant(@PathVariable("plant_id") Long plantId) {
		GetPlantResponse response = plantService.getPlant(plantId);
		return ResponseEntity.ok(response);
	}

	@GetMapping(GET_All_PLANTS)
	public ResponseEntity<GetAllPlantsResponse> getAllPlants() {
		GetAllPlantsResponse response = plantService.getAllPlants();
		return ResponseEntity.ok(response);
	}

}
