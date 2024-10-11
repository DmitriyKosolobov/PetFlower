package ru.petflower.controller.responses.device;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;

public record GetSpecificTypeMeasureResponse(
        @JsonProperty(value = "measure_id")
        Long measure_id,
        @JsonProperty(value = "device_id")
        Long device_id,
        OffsetDateTime date,
        Integer measure
) {
}