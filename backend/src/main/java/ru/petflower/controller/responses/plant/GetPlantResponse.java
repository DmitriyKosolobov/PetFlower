package ru.petflower.controller.responses.plant;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GetPlantResponse (

		@JsonProperty(value = "plant_id")
		Long plantId,

		@JsonProperty(value = "plant_name")
		String plantName,

		@JsonProperty(value = "max_light_lux")
		Integer maxLightLux,

		@JsonProperty(value = "min_light_lux")
		Integer minLightLux,

		@JsonProperty(value = "max_temp")
		Integer maxTemp,

		@JsonProperty(value = "min_temp")
		Integer minTemp,

		@JsonProperty(value = "max_env_humid")
		Integer maxEnvHumid,

		@JsonProperty(value = "min_env_humid")
		Integer minEnvHumid,

		@JsonProperty(value = "max_soil_moist")
		Integer maxSoilMoist,

		@JsonProperty(value = "min_soil_moist")
		Integer minSoilMoist,

		@JsonProperty(value = "plant_info")
		String plantInfo
) {
}
