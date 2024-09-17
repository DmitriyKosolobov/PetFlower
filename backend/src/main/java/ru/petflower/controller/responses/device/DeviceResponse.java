package ru.petflower.controller.responses.device;

public record DeviceResponse(
    Long deviceId,
    String deviceName,
    String key,
    Long userId,
    Double batteryLevel
) {
}
