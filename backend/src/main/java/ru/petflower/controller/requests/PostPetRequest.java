package ru.petflower.controller.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PostPetRequest(

		@JsonProperty(value = "pet_name")
		String petName
) {
}
