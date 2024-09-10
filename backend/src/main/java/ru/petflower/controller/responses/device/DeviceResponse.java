package ru.petflower.controller.responses.device;

public record DeviceResponse(
    Long deviceId,
    String key,
    Long userId,
    Double batteryLevel
) {
}
