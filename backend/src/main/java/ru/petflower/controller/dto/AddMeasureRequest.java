package ru.petflower.controller.dto;

public record AddMeasureRequest(
        Integer temperature,
        Integer humidity,
        Integer light,
        Integer moisture,
        Double battery
) {}
