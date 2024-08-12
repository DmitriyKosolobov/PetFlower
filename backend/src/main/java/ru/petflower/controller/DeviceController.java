package ru.petflower.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.petflower.controller.dto.AddMeasureRequest;
import ru.petflower.service.DeviceService;

@RestController
public class DeviceController {

    private final DeviceService deviceService;


    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

//    @PostMapping("/devices")
//    public ResponseEntity<?> addDevice(@RequestBody AddDeviceRequest addDeviceRequest) {
//        deviceService.register(addDeviceRequest);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
//
//    @DeleteMapping("/devices/{key}")
//    public ResponseEntity<?> deleteDevice(@PathVariable Long key) {
//        deviceService.unregister(key);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }

    @PostMapping("devices/{key}/measure")
    public ResponseEntity<?> addMeasure(@PathVariable Long key, @RequestBody AddMeasureRequest addMeasureRequest) {
        deviceService.addMeasure(key, addMeasureRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
