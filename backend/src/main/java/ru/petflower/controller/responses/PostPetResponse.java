package ru.petflower.controller.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PostPetResponse (

		@JsonProperty(value = "pet_name")
		String petName
) {
}
