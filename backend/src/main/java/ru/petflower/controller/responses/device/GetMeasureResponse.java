package ru.petflower.controller.responses.device;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;

public record GetMeasureResponse (

		@JsonProperty(value = "measure_id")
		Long measure_id,

		@JsonProperty(value = "device_id")
		Long device_id,

		OffsetDateTime date,

		@JsonProperty(value = "light_lux")
		Integer lightLux,

		Integer temp,

		@JsonProperty(value = "env_humid")
		Integer envHumid,

		@JsonProperty(value = "soil_moist")
		Integer soilMoist,

		@JsonProperty(value = "battery_level")
		Double batter_level
) {
}
