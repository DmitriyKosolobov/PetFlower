package ru.petflower.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.petflower.controller.requests.pet.PostPetRequest;
import ru.petflower.controller.responses.device.GetAllMeasuresResponse;
import ru.petflower.controller.responses.device.GetAllSpecificTypeMeasuresResponse;
import ru.petflower.controller.responses.pet.DeletePetResponse;
import ru.petflower.controller.responses.pet.GetFullPetInfo;
import ru.petflower.controller.responses.pet.GetPetsResponse;
import ru.petflower.controller.responses.pet.PostPetResponse;
import ru.petflower.enums.MeasureType;
import ru.petflower.service.PetService;
import ru.petflower.service.jwt.AuthService;

@RestController
@RequiredArgsConstructor
public class PetController {

	public static final String API_PREFIX = "pets";

	public static final String GET_FULL_PET_INFO = API_PREFIX + "/{pet_id}";
	public static final String GET_PETS = API_PREFIX;
	public static final String POST_PET = API_PREFIX;
	public static final String PUT_PET = API_PREFIX + "/{pet_id}";
	public static final String DELETE_PET = API_PREFIX + "/{pet_id}";
	public static final String GET_MEASURES = API_PREFIX + "/{pet_id}/measures";

	private final PetService petService;
	private final AuthService authService;

	@GetMapping(GET_FULL_PET_INFO)
	public ResponseEntity<GetFullPetInfo> getFullPetInfo(@PathVariable("pet_id") Long petId) {
		String username = (String) authService.getAuthInfo().getPrincipal();
		GetFullPetInfo response = petService.getPet(username, petId);
		return ResponseEntity.ok(response);
	}

	@GetMapping(GET_PETS)
	public ResponseEntity<GetPetsResponse> getPets() {
		String username = (String) authService.getAuthInfo().getPrincipal();
		GetPetsResponse response = petService.getAllPets(username);
		return ResponseEntity.ok(response);
	}

	@PostMapping(POST_PET)
	public ResponseEntity<PostPetResponse> addPet(@RequestBody PostPetRequest request) {
		String username = (String) authService.getAuthInfo().getPrincipal();
		PostPetResponse response = petService.addPet(username, request);
		return ResponseEntity.ok(response);
	}

	@PutMapping(PUT_PET)
	public ResponseEntity<PostPetResponse> changePet(@PathVariable("pet_id") Long petId, @RequestBody PostPetRequest request) {
		String username = (String) authService.getAuthInfo().getPrincipal();
		PostPetResponse response = petService.changePet(username, petId, request);
		return ResponseEntity.ok(response);
	}

	@DeleteMapping(DELETE_PET)
	public ResponseEntity<DeletePetResponse> deletePet(@PathVariable("pet_id") Long petId) {
		String username = (String) authService.getAuthInfo().getPrincipal();
		DeletePetResponse response = petService.deletePet(username, petId);
		return ResponseEntity.ok(response);
	}

	@GetMapping(GET_MEASURES)
	public ResponseEntity<?> getMeasures(
			@PathVariable("pet_id") Long petId,
			@RequestParam(value = "measure_type", required = false) String measureType,
			@RequestParam(value = "size", required = false, defaultValue = "20") Integer size
	) {
		String username = (String) authService.getAuthInfo().getPrincipal();
		if (measureType != null) {
			GetAllSpecificTypeMeasuresResponse response = petService.getSpecificTypeMeasures(
					username, petId, size,
					MeasureType.valueOf(measureType.toUpperCase())
			);
			return ResponseEntity.ok(response);
		} else {
			GetAllMeasuresResponse response = petService.getMeasures(username, petId, size);
			return ResponseEntity.ok(response);
		}
	}
}
