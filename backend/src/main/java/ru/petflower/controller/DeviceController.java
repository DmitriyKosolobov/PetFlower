package ru.petflower.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.petflower.controller.requests.device.AddDeviceRequest;
import ru.petflower.controller.requests.device.AddMeasureRequest;
import ru.petflower.controller.responses.device.GetAllDevicesResponse;
import ru.petflower.controller.responses.device.DeviceResponse;
import ru.petflower.service.DeviceService;
import ru.petflower.service.jwt.AuthService;

@RestController
public class DeviceController {
    public static final String API_PREFIX = "devices";
    public static final String GET_ALL_DEVICES = API_PREFIX;
    public static final String GET_DEVICE = API_PREFIX + "/{key}";
    public static final String POST_DEVICE = API_PREFIX;
    public static final String DELETE_DEVICE = API_PREFIX + "/{key}";
    public static final String POST_MEASURE = API_PREFIX + "/{key}/measure";

    private final DeviceService deviceService;
    private final AuthService authService;

    public DeviceController(DeviceService deviceService, AuthService authService) {
        this.deviceService = deviceService;
        this.authService = authService;
    }

    @GetMapping(GET_ALL_DEVICES)
    public ResponseEntity<GetAllDevicesResponse> getAllDevices() {
        String username = (String) authService.getAuthInfo().getPrincipal();
        GetAllDevicesResponse response = deviceService.findAllDevices(username);
        return ResponseEntity.ok(response);
    }

    @GetMapping(GET_DEVICE)
    public ResponseEntity<DeviceResponse> getDevice(@PathVariable String key) {
        String username = (String) authService.getAuthInfo().getPrincipal();
        DeviceResponse response = deviceService.findDevice(username, key);
        return ResponseEntity.ok(response);
    }

    @PostMapping(POST_DEVICE)
    public ResponseEntity<DeviceResponse> addDevice(@RequestBody @Valid AddDeviceRequest addDeviceRequest) {
        String username = (String) authService.getAuthInfo().getPrincipal();
        DeviceResponse response = deviceService.registerDevice(username, addDeviceRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(DELETE_DEVICE)
    public ResponseEntity<DeviceResponse> deleteDevice(@PathVariable String key) {
        String username = (String) authService.getAuthInfo().getPrincipal();
        DeviceResponse response = deviceService.unregisterDevice(username, key);
        return ResponseEntity.ok(response);
    }

    @PostMapping(POST_MEASURE)
    public ResponseEntity<?> addMeasure(@PathVariable String key, @RequestBody AddMeasureRequest addMeasureRequest) {
        deviceService.addMeasure(key, addMeasureRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
