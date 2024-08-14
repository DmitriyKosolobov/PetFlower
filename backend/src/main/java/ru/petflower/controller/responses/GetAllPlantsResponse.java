package ru.petflower.controller.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record GetAllPlantsResponse(

		@JsonProperty(value = "plants_list")
		List<GetPlantResponse> plantsList
) {
}
