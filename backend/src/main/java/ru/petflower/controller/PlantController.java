package ru.petflower.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.petflower.controller.requests.PutPlantRequest;
import ru.petflower.controller.responses.GetAllPlantsResponse;
import ru.petflower.controller.responses.GetPlantResponse;
import ru.petflower.controller.responses.PutPlantResponse;

@RestController
@RequiredArgsConstructor
public class PlantController {

	public static final String API_PREFIX = "/api/{user_id}/{pet_id}";

	public static final String GET_PLANT = API_PREFIX + "/get_plant";
	public static final String GET_All_PLANTS = API_PREFIX + "/get_all_plants";
	public static final String PUT_PLANT = API_PREFIX + "/put_plant";
	public static final String DELETE_PLANT = API_PREFIX + "/delete_plant";

	//TODO add plantService

	@GetMapping(GET_PLANT)
	public ResponseEntity<GetPlantResponse> getPlant(@PathVariable("user_id") Long user_id, @PathVariable("pet_id") Long pet_id) {
		return null;
	}

	@GetMapping(GET_All_PLANTS)
	public ResponseEntity<GetAllPlantsResponse> getAllPlants(@PathVariable Long user_id, @PathVariable Long pet_id) {
		return null;
	}

	@PutMapping(PUT_PLANT)
	public ResponseEntity<PutPlantResponse> putPlant(@PathVariable Long user_id, @PathVariable Long pet_id, @RequestBody PutPlantRequest request)  {
		return null;
	}

}
