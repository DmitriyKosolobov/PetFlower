package ru.petflower.controller.responses.pet;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PostPetResponse (
		@JsonProperty(value = "pet_id")
		Long petId,
		@JsonProperty(value = "pet_name")
		String petName,
		@JsonProperty(value = "user_id")
		Long userId,
		@JsonProperty(value = "plant_id")
		Long plantId,
		@JsonProperty(value = "device_id")
		Long deviceId
) {
}
