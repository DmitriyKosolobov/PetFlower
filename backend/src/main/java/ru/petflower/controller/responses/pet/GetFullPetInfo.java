package ru.petflower.controller.responses.pet;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.petflower.controller.responses.device.GetMeasureResponse;

public record GetFullPetInfo (

		@JsonProperty(value = "pet_id")
		Long petId,

		@JsonProperty(value = "pet_name")
		String petName,

		@JsonProperty(value = "plant_id")
		Long plantId,

		@JsonProperty(value = "plant_name")
		String plantName,

		@JsonProperty(value = "device_id")
		Long deviceId,

		@JsonProperty(value = "last_measure")
		GetMeasureResponse getMeasureResponse
) {
}
