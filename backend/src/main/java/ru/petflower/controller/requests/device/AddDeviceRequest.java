package ru.petflower.controller.requests.device;

import jakarta.validation.constraints.NotEmpty;

public record AddDeviceRequest (
    @NotEmpty String key
) {
}