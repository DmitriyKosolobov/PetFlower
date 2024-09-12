package ru.petflower.controller.responses.device;

import java.util.List;

public record GetAllDevicesResponse (
    List<DeviceResponse> devices
) {
}