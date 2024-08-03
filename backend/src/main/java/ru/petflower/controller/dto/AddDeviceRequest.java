package ru.petflower.controller.dto;

public record AddDeviceRequest(
        Long key,
        String username,
        String plantName
) {}
