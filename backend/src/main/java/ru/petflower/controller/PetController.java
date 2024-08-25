package ru.petflower.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.petflower.controller.requests.pet.PostPetRequest;
import ru.petflower.controller.responses.device.GetAllMeasuresResponse;
import ru.petflower.controller.responses.pet.DeletePetResponse;
import ru.petflower.controller.responses.pet.GetFullPetInfo;
import ru.petflower.controller.responses.pet.GetPetsResponse;
import ru.petflower.controller.responses.pet.PostPetResponse;

@RestController
@RequiredArgsConstructor
public class PetController {

	public static final String API_PREFIX = "pets/";

	public static final String GET_FULL_PET_INFO = API_PREFIX + "{pet_id}";
	public static final String GET_PETS = API_PREFIX;
	public static final String POST_PET = API_PREFIX;
	public static final String PUT_PET = API_PREFIX + "{pet_id}";
	public static final String DELETE_PET = API_PREFIX + "{pet_id}";
	public static final String GET_MEASURES = API_PREFIX + "{pet_id}/measures";

	//TODO add petService

	@GetMapping(GET_FULL_PET_INFO)
	public ResponseEntity<GetFullPetInfo> getFullPetInfo(@PathVariable("pet_id") Long petId) {
		//TODO
		GetFullPetInfo response = null;
		return ResponseEntity.ok(response);
	}

	@GetMapping(GET_PETS)
	public ResponseEntity<GetPetsResponse> getPets() {
		//TODO
		GetPetsResponse response = null;
		return ResponseEntity.ok(response);
	}

	@PostMapping(POST_PET)
	public ResponseEntity<PostPetResponse> addPet(@RequestBody PostPetRequest request) {
		//TODO
		PostPetResponse response = null;
		return ResponseEntity.ok(response);
	}

	@PutMapping(PUT_PET)
	public ResponseEntity<PostPetResponse> changePet(@PathVariable("pet_id") Long petId, @RequestBody PostPetRequest request) {
		//TODO
		PostPetResponse response = null;
		return ResponseEntity.ok(response);
	}

	@DeleteMapping(DELETE_PET)
	public ResponseEntity<DeletePetResponse> deletePet(@PathVariable("pet_id") Long petId) {
		//TODO
		DeletePetResponse response = null;
		return ResponseEntity.ok(response);
	}

	@GetMapping(GET_MEASURES)
	public ResponseEntity<GetAllMeasuresResponse> getMeasures(@PathVariable("pet_id") Long petId) {
		//TODO:
		GetAllMeasuresResponse response = null;
		return ResponseEntity.ok(response);
	}
}
