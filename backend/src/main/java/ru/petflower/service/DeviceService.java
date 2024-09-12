package ru.petflower.service;

import ru.petflower.controller.requests.device.AddDeviceRequest;
import ru.petflower.controller.requests.device.AddMeasureRequest;
import ru.petflower.controller.responses.device.DeviceResponse;
import ru.petflower.controller.responses.device.GetAllDevicesResponse;

public interface DeviceService {
    GetAllDevicesResponse findAllDevices(String username);
    DeviceResponse findDevice(String username, String key);
    DeviceResponse registerDevice(String username, AddDeviceRequest addDeviceRequest);
    DeviceResponse unregisterDevice(String username, String key);
    void addMeasure(String key, AddMeasureRequest addMeasureRequest);

}
