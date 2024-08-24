package ru.petflower.controller.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PutPlantRequest (

		@JsonProperty(value = "new_plant_id")
		Long newPlatId
) {
}
