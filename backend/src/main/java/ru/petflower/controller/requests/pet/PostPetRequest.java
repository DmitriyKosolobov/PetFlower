package ru.petflower.controller.requests.pet;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PostPetRequest(

		@JsonProperty(value = "pet_name")
		String petName,
		@JsonProperty(value = "plant_id")
		Long plantId,
		@JsonProperty(value = "device_id")
		Long deviceId
) {
}
