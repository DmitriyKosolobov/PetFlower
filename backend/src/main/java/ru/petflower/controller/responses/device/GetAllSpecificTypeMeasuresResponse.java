package ru.petflower.controller.responses.device;

import java.util.List;

public record GetAllSpecificTypeMeasuresResponse(
        List<GetSpecificTypeMeasureResponse> measures
) {
}
