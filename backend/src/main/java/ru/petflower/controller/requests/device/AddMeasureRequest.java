package ru.petflower.controller.requests.device;

public record AddMeasureRequest(
        Integer temperature,
        Integer humidity,
        Integer light,
        Integer moisture,
        Double battery
) {}
