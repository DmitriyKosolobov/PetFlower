package ru.petflower.controller.responses.plant;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PutPlantResponse (

		@JsonProperty(value = "old_plant_id")
		Long oldPlantId,

		@JsonProperty(value = "old_plant_name")
		String oldPlantName,

		@JsonProperty(value = "new_plant_id")
		Long newPlantId,

		@JsonProperty(value = "new_plant_name")
		String newPlantName
) {
}
