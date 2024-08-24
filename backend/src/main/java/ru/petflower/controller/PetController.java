package ru.petflower.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.petflower.controller.requests.PostPetRequest;
import ru.petflower.controller.responses.DeletePetResponse;
import ru.petflower.controller.responses.GetFullPetInfo;
import ru.petflower.controller.responses.GetPetsResponse;
import ru.petflower.controller.responses.PostPetResponse;

@RestController
@RequiredArgsConstructor
public class PetController {

	public static final String API_PREFIX = "/pets/{user_id}";

	public static final String GET_FULL_PET_INFO = API_PREFIX + "/{pet_id}";
	public static final String GET_PETS = API_PREFIX + "/get_pets";
	public static final String POST_PET = API_PREFIX + "/post_pet";
	public static final String PUT_PET = API_PREFIX + "/put_pet";
	public static final String DELETE_PET = API_PREFIX + "/{pet_id}/delete_pet";

	//TODO add petService

	@GetMapping(GET_FULL_PET_INFO)
	public ResponseEntity<GetFullPetInfo> getFullPetInfo(@PathVariable("user_id") Long user, @PathVariable("pet_id") Long petId) {
		return null;
	}

	@GetMapping(GET_PETS)
	public ResponseEntity<GetPetsResponse> getPets(@PathVariable("user_id") Long user) {
		return null;
	}

	@PostMapping(POST_PET)
	private ResponseEntity<PostPetResponse> postPet(@PathVariable("user_id") Long user, @RequestBody PostPetRequest request) {
		return null;
	}

	@PutMapping(PUT_PET)
	private ResponseEntity<PostPetResponse> changePetName(@PathVariable("user_id") Long user, @RequestBody PostPetRequest request) {
		return null;
	}

	@DeleteMapping(DELETE_PET)
	private ResponseEntity<DeletePetResponse> deletePet(@PathVariable("user_id") Long user, @PathVariable("pet_id") Long petId) {
		return null;
	}
}
