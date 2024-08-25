package ru.petflower.controller.responses;

public record ErrorResponse(
        String code,
        String description
) {}