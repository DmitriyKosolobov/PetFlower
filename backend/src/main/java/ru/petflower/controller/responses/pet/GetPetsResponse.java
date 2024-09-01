package ru.petflower.controller.responses.pet;


import java.util.List;

public record GetPetsResponse (
		List<PetResponse> pets
) {
}
