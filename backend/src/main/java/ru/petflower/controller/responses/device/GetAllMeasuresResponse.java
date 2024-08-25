package ru.petflower.controller.responses.device;

import java.util.List;

public record GetAllMeasuresResponse (
        List<GetMeasureResponse> measures
) {
}
