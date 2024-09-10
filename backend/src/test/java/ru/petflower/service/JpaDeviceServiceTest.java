package ru.petflower.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.petflower.IntegrationTest;
//import ru.petflower.controller.dto.AddDeviceRequest;
import ru.petflower.domain.JpaDeviceRepository;
import ru.petflower.domain.JpaPlantRepository;
import ru.petflower.domain.JpaUserAccountRepository;
import ru.petflower.service.impl.JpaDeviceService;

@SpringBootTest
public class JpaDeviceServiceTest extends IntegrationTest {

    @Autowired
    private JpaDeviceService jpaDeviceService;

    @Autowired
    private JpaUserAccountRepository jpaUserAccountRepository;

    @Autowired
    private JpaPlantRepository jpaPlantRepository;

    @Autowired
    private JpaDeviceRepository jpaDeviceRepository;

//    @Test
//    @Transactional
//    @Rollback
//    void registerTest() {
//        UserAccount userAccount = new UserAccount("user","email","password");
//        Plant plant = new Plant("plant","info",1,1,1,1);
//        jpaUserAccountRepository.save(userAccount);
//        jpaPlantRepository.save(plant);
//
//        AddDeviceRequest request = new AddDeviceRequest(1L,"user","plant");
//        jpaDeviceService.register(request);
//
//        List<Device> devices = jpaDeviceRepository.findAll();
//        Device device = devices.get(0);
//
//        Assertions.assertEquals(1, devices.size());
//        Assertions.assertEquals(1L, device.getKey());
//        Assertions.assertEquals(userAccount, device.getUserAccount());
//        Assertions.assertEquals(plant, device.getPlant());
//        Assertions.assertEquals(100.0, device.getBattery());
//        Assertions.assertEquals(1, userAccount.getDevices().size());
//        Assertions.assertEquals(device, userAccount.getDevices().get(0));
//        Assertions.assertEquals(1, plant.getDevices().size());
//        Assertions.assertEquals(device, plant.getDevices().get(0));
//    }
//
//    @Test
//    @Transactional
//    @Rollback
//    void unregisterTest() {
//        UserAccount userAccount = new UserAccount("user","email","password");
//        Plant plant = new Plant("plant","info",1,1,1,1);
//        jpaUserAccountRepository.save(userAccount);
//        jpaPlantRepository.save(plant);
//
//        AddDeviceRequest request = new AddDeviceRequest(1L,"user","plant");
//        jpaDeviceService.register(request);
//
//        List<Device> devices = jpaDeviceRepository.findAll();
//        Device device = devices.get(0);
//
//        Assertions.assertEquals(1, devices.size());
//        Assertions.assertEquals(1L, device.getKey());
//
//        jpaDeviceService.unregister(1L);
//        devices = jpaDeviceRepository.findAll();
//
//        Assertions.assertEquals(0, devices.size());
//        Assertions.assertEquals(0, userAccount.getDevices().size());
//        Assertions.assertEquals(0, plant.getDevices().size());
//    }

//    @Test
//    @Transactional
//    @Rollback
//    void addMeasureTest() {
//        UserAccount userAccount = new UserAccount("user","email","password");
//        Plant plant = new Plant("plant","info",1,1,1,1);
//        jpaUserAccountRepository.save(userAccount);
//        jpaPlantRepository.save(plant);
//
//        AddDeviceRequest request = new AddDeviceRequest(1L,"user","plant");
//        jpaDeviceService.register(request);
//
//        AddMeasureRequest measureRequest = new AddMeasureRequest(1,1,1,1,50.0);
//        jpaDeviceService.addMeasure(1L, measureRequest);
//
//        Device device = jpaDeviceRepository.findByKey(1L).get();
//
//        Assertions.assertEquals(1, device.getMeasures().size());
//        Assertions.assertEquals(device, device.getMeasures().get(0).getDevice());
//        Assertions.assertEquals(1, device.getMeasures().get(0).getHumidity());
//        Assertions.assertEquals(1, device.getMeasures().get(0).getLight());
//        Assertions.assertEquals(1, device.getMeasures().get(0).getTemperature());
//        Assertions.assertEquals(1, device.getMeasures().get(0).getMoisture());
//        Assertions.assertEquals(50.0, device.getBattery());
//
//    }

}
