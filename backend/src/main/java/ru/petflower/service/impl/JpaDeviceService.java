package ru.petflower.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.petflower.controller.dto.AddMeasureRequest;
import ru.petflower.domain.JpaDeviceRepository;
import ru.petflower.domain.JpaMeasureRepository;
import ru.petflower.domain.JpaPlantRepository;
import ru.petflower.domain.JpaUserAccountRepository;
import ru.petflower.domain.entity.Device;
import ru.petflower.domain.entity.Measure;
import ru.petflower.domain.entity.Plant;
import ru.petflower.domain.entity.UserAccount;
import ru.petflower.exception.ExistDeviceException;
import ru.petflower.exception.NotFoundDeviceException;
import ru.petflower.exception.NotFoundPlantException;
import ru.petflower.exception.NotFoundUserException;
import ru.petflower.service.DeviceService;

import java.util.Optional;

@Service
public class JpaDeviceService implements DeviceService {

    private final JpaDeviceRepository jpaDeviceRepository;
    private final JpaMeasureRepository jpaMeasureRepository;
    private final JpaUserAccountRepository jpaUserAccountRepository;
    private final JpaPlantRepository jpaPlantRepository;

    public JpaDeviceService(JpaDeviceRepository jpaDeviceRepository, JpaMeasureRepository jpaMeasureRepository,
                            JpaUserAccountRepository jpaUserAccountRepository, JpaPlantRepository jpaPlantRepository) {
        this.jpaDeviceRepository = jpaDeviceRepository;
        this.jpaMeasureRepository = jpaMeasureRepository;
        this.jpaUserAccountRepository = jpaUserAccountRepository;
        this.jpaPlantRepository = jpaPlantRepository;
    }

    @Override
    @Transactional
    public void addMeasure(Long key, AddMeasureRequest addMeasureRequest) {
        //найти устройство по ключу
        //обновить батарею
        //добавить измерение
        Optional<Device> optionalDevice = jpaDeviceRepository.findByKey(key);
        if(optionalDevice.isEmpty()){
            throw new NotFoundDeviceException();
        } else {
            Device device = optionalDevice.get();
            Measure measure = new Measure(addMeasureRequest);
            device.addMeasure(measure);

            //jpaDeviceRepository.save(device); //излишне
            jpaMeasureRepository.save(measure);
        }
    }

//    @Override
//    @Transactional
//    public void register(AddDeviceRequest addDeviceRequest) {
//        //проверить что устройство не зарегестрировано
//        //проверить что пользователь зарегестрирован
//        //проверить что растение такое существует
//        Optional<Device> optionalDevice = jpaDeviceRepository.findByKey(addDeviceRequest.key());
//        if(optionalDevice.isPresent()) {
//            throw new ExistDeviceException();
//        }
//
//        Optional<UserAccount> optionalUserAccount = jpaUserAccountRepository.findByUsername(addDeviceRequest.username());
//        if(optionalUserAccount.isEmpty()) {
//            throw new NotFoundUserException();
//        }
//
//        Optional<Plant> optionalPlant = jpaPlantRepository.findByName(addDeviceRequest.plantName());
//        if(optionalPlant.isEmpty()) {
//            throw new NotFoundPlantException();
//        }
//
//        UserAccount userAccount = optionalUserAccount.get();
//        Plant plant = optionalPlant.get();
//
//        Device device = new Device(addDeviceRequest.key());
//        userAccount.addDevice(device);
//        plant.addDevice(device);
//
//        jpaDeviceRepository.save(device);
//        //jpaUserAccountRepository.save(userAccount); //излишне
//        //jpaPlantRepository.save(plant); //излишне
//    }
//
//    @Override
//    @Transactional
//    public void unregister(Long key) {
//        //проверить что зарегестрировано устройство
//        Optional<Device> optionalDevice = jpaDeviceRepository.findByKey(key);
//        if(optionalDevice.isEmpty()) {
//            throw new NotFoundDeviceException();
//        }
//
//        Device device = optionalDevice.get();
//        device.getPlant().removeDevice(device);
//        device.getUserAccount().removeDevice(device);
//
//        jpaDeviceRepository.delete(device);
//    }
}
