package ru.petflower.controller.dto;

public record ErrorResponse(
        String code,
        String description,
        String exceptionName,
        String exceptionMessage
) {}