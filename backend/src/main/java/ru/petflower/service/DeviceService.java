package ru.petflower.service;

import ru.petflower.controller.dto.AddMeasureRequest;

public interface DeviceService {
    void addMeasure(Long key, AddMeasureRequest addMeasureRequest);

    //void register(AddDeviceRequest addDeviceRequest);

    //void unregister(Long key);

}
