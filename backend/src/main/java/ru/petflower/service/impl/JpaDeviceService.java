package ru.petflower.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.petflower.controller.requests.device.AddDeviceRequest;
import ru.petflower.controller.requests.device.AddMeasureRequest;
import ru.petflower.controller.responses.device.DeviceResponse;
import ru.petflower.controller.responses.device.GetAllDevicesResponse;
import ru.petflower.domain.*;
import ru.petflower.domain.entity.Device;
import ru.petflower.domain.entity.Measure;
import ru.petflower.domain.entity.Pet;
import ru.petflower.domain.entity.UserAccount;
import ru.petflower.exception.CustomException;
import ru.petflower.exception.ErrorType;
import ru.petflower.service.DeviceService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class JpaDeviceService implements DeviceService {

    private final JpaDeviceRepository jpaDeviceRepository;
    private final JpaMeasureRepository jpaMeasureRepository;
    private final JpaUserAccountRepository jpaUserAccountRepository;
    private final JpaPetRepository jpaPetRepository;

    public JpaDeviceService(JpaDeviceRepository jpaDeviceRepository, JpaMeasureRepository jpaMeasureRepository,
                            JpaUserAccountRepository jpaUserAccountRepository, JpaPetRepository jpaPetRepository) {
        this.jpaDeviceRepository = jpaDeviceRepository;
        this.jpaMeasureRepository = jpaMeasureRepository;
        this.jpaUserAccountRepository = jpaUserAccountRepository;
        this.jpaPetRepository = jpaPetRepository;
    }

    @Override
    @Transactional
    public void addMeasure(String key, AddMeasureRequest addMeasureRequest) {
        Optional<Device> optionalDevice = jpaDeviceRepository.findByKey(key);
        if(optionalDevice.isEmpty()){
            throw new CustomException(ErrorType.NOT_FOUND_EXCEPTION, "Устройство не найдено");
        } else {
            Device device = optionalDevice.get();
            Measure measure = new Measure(addMeasureRequest);
            device.addMeasure(measure);

            //jpaDeviceRepository.save(device); //излишне
            jpaMeasureRepository.save(measure);
        }
    }

    @Override
    public GetAllDevicesResponse findAllDevices(String username) {
        Optional<UserAccount> optionalUserAccount = jpaUserAccountRepository.findByUsername(username);
        if(optionalUserAccount.isEmpty()){
            throw new CustomException(ErrorType.NOT_FOUND_EXCEPTION, "Пользователь не найден");
        } else {
            UserAccount userAccount = optionalUserAccount.get();
            List<Device> devices = userAccount.getDevices();
            List<DeviceResponse> deviceResponses = devices.stream()
                    .map(device -> {
                        Double batteryLevel = null;
                        if (!device.getMeasures().isEmpty()) {
                            batteryLevel = device.getMeasures().getLast().getBatteryLevel();
                        }
                        return new DeviceResponse(
                                        device.getId(),
                                        device.getKey(),
                                        device.getUserAccount().getId(),
                                        batteryLevel);
                    })
                    .toList();
            return new GetAllDevicesResponse(deviceResponses);
        }
    }

    @Override
    public DeviceResponse findDevice(String username, String key) {
        Optional<UserAccount> optionalUserAccount = jpaUserAccountRepository.findByUsername(username);
        if(optionalUserAccount.isEmpty()){
            throw new CustomException(ErrorType.NOT_FOUND_EXCEPTION, "Пользователь не найден");
        } else {
            UserAccount userAccount = optionalUserAccount.get();
            List<Device> devices = userAccount.getDevices().stream()
                    .filter(device -> device.getKey().equals(key))
                    .toList();
            if(devices.isEmpty()) {
                throw new CustomException(ErrorType.NOT_FOUND_EXCEPTION, "Устройство не найдено");
            } else {
                Device device = devices.getFirst();
                Double batteryLevel = null;
                if (!device.getMeasures().isEmpty()) {
                    batteryLevel = device.getMeasures().getLast().getBatteryLevel();
                }
                return new DeviceResponse(
                        device.getId(),
                        device.getKey(),
                        device.getUserAccount().getId(),
                        batteryLevel
                );
            }
        }
    }

    @Override
    @Transactional
    public DeviceResponse registerDevice(String username, AddDeviceRequest addDeviceRequest) {
        Optional<Device> optionalDevice = jpaDeviceRepository.findByKey(addDeviceRequest.key());
        if(optionalDevice.isPresent()) {
            throw new CustomException(ErrorType.ALREADY_EXIST_EXCEPTION, "Устройство уже зарегистрировано");
        }

        Optional<UserAccount> optionalUserAccount = jpaUserAccountRepository.findByUsername(username);
        if(optionalUserAccount.isEmpty()) {
            throw new CustomException(ErrorType.NOT_FOUND_EXCEPTION, "Пользователь не найден");
        }

//        Optional<Pet> optionalPet = jpaPetRepository.findByName(addDeviceRequest.petName());
//        if(optionalPet.isEmpty()) {
//            throw new CustomException(ErrorType.NOT_FOUND_EXCEPTION, "Питомец не найден");
//        }
//        Pet pet = optionalPet.get();

        UserAccount userAccount = optionalUserAccount.get();

        Device device = new Device(addDeviceRequest.key());
        //device.addPet(pet);
        userAccount.addDevice(device);

        jpaDeviceRepository.save(device);
        //jpaUserAccountRepository.save(userAccount); //излишне
        //jpaPlantRepository.save(plant); //излишне
        return new DeviceResponse(
                device.getId(),
                device.getKey(),
                device.getUserAccount().getId(),
                null
        );
    }

    @Override
    @Transactional
    public DeviceResponse unregisterDevice(String username, String key) {
        Optional<Device> optionalDevice = jpaDeviceRepository.findByKey(key);
        if(optionalDevice.isEmpty()) {
            throw new CustomException(ErrorType.NOT_FOUND_EXCEPTION, "Устройство не найдено");
        }
        Device device = optionalDevice.get();
        if(!device.getUserAccount().getUsername().equals(username)) {
            throw new CustomException(ErrorType.NOT_FOUND_EXCEPTION, "Устройство не найдено");
        }

        Double batteryLevel = null;
        if (!device.getMeasures().isEmpty()) {
            batteryLevel = device.getMeasures().getLast().getBatteryLevel();
        }
        DeviceResponse response = new DeviceResponse(
                device.getId(),
                device.getKey(),
                device.getUserAccount().getId(),
                batteryLevel
        );

        Pet pet = device.getPet();
        if (Objects.nonNull(pet)) {
            device.removePet(pet);
        }
        device.getUserAccount().removeDevice(device);

        jpaDeviceRepository.delete(device);
        return response;
    }
}
