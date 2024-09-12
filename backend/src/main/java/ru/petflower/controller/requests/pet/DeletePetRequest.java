package ru.petflower.controller.requests.pet;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DeletePetRequest (

		@JsonProperty(value = "pet_name")
		String petName,

		@JsonProperty(value = "pet_id")
		Long petId
) {
}
