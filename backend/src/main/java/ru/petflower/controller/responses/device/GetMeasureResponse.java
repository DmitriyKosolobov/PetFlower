package ru.petflower.controller.responses.device;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record GetMeasureResponse (

		@JsonProperty(value = "measure_id")
		Long measure_id,

		@JsonProperty(value = "device_id")
		Long device_id,

		LocalDateTime date,

		@JsonProperty(value = "light_lux")
		Double lightLux,

		Double temp,

		@JsonProperty(value = "env_humid")
		Double envHumid,

		@JsonProperty(value = "soil_moist")
		Double soilMoist,

		@JsonProperty(value = "battery_level")
		Double batter_level
) {
}
