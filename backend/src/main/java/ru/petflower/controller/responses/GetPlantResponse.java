package ru.petflower.controller.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GetPlantResponse (

		@JsonProperty(value = "plant_id")
		Long plantId,

		@JsonProperty(value = "plant_name")
		String plantName,

		@JsonProperty(value = "max_light_lux")
		Double maxLightLux,

		@JsonProperty(value = "min_light_lux")
		Double minLightLux,

		@JsonProperty(value = "max_temp")
		Double maxTemp,

		@JsonProperty(value = "min_temp")
		Double minTemp,

		@JsonProperty(value = "max_env_humid")
		Double maxEnvHumid,

		@JsonProperty(value = "min_env_humid")
		Double minEnvHumid,

		@JsonProperty(value = "max_soil_moist")
		Double maxSoilMoist,

		@JsonProperty(value = "min_soil_moist")
		Double minSoilMoist
) {
}
