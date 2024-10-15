package ru.petflower.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.petflower.controller.requests.pet.PostPetRequest;
import ru.petflower.controller.responses.device.GetAllMeasuresResponse;
import ru.petflower.controller.responses.device.GetAllSpecificTypeMeasuresResponse;
import ru.petflower.controller.responses.device.GetMeasureResponse;
import ru.petflower.controller.responses.device.GetSpecificTypeMeasureResponse;
import ru.petflower.controller.responses.pet.*;
import ru.petflower.domain.JpaDeviceRepository;
import ru.petflower.domain.JpaPetRepository;
import ru.petflower.domain.JpaPlantRepository;
import ru.petflower.domain.JpaUserAccountRepository;
import ru.petflower.domain.entity.*;
import ru.petflower.enums.MeasureType;
import ru.petflower.exception.CustomException;
import ru.petflower.exception.ErrorType;
import ru.petflower.service.PetService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JpaPetService implements PetService {

    private final JpaPetRepository jpaPetRepository;
    private final JpaUserAccountRepository jpaUserAccountRepository;
    private final JpaDeviceRepository jpaDeviceRepository;
    private final JpaPlantRepository jpaPlantRepository;

    private final String HAPPY_CONDITION = "happy";
    private final String OK_CONDITION = "ok";
    private final String SAD_CONDITION = "sad";
    private final Integer sizeOfMeasuresList = 20;

    @Value("${measures.deviation}")
    private Integer deviation;

    @Override
    @Transactional
    public PostPetResponse addPet(String username, PostPetRequest request) {
        Optional<UserAccount> optionalUserAccount = jpaUserAccountRepository.findByUsername(username);
        if(optionalUserAccount.isEmpty()) {
            throw new CustomException(ErrorType.NOT_FOUND_EXCEPTION, "Пользователь не найден");
        }

        UserAccount userAccount = optionalUserAccount.get();
        List<Pet> pets = userAccount.getPets().stream()
                .filter(pet -> pet.getName().equals(request.petName()))
                .toList();
        if(!pets.isEmpty()) {
            throw new CustomException(ErrorType.ALREADY_EXIST_EXCEPTION, "Питомец с таким именем уже существует");
        }
        Pet pet = new Pet(request.petName());
        userAccount.addPet(pet);

        if(request.deviceId() != null) {
            Optional<Device> optionalDevice = jpaDeviceRepository.findById(request.deviceId());
            if(optionalDevice.isEmpty()) {
                throw new CustomException(ErrorType.NOT_FOUND_EXCEPTION, "Устройство не найдено");
            }
            Device device = optionalDevice.get();
            if(device.getPet() != null) {
                throw new CustomException(ErrorType.ALREADY_EXIST_EXCEPTION, "Устройство уже привязано к другому питомцу");
            }
            device.addPet(pet);
        }

        if(request.plantId() != null) {
            Optional<Plant> optionalPlant = jpaPlantRepository.findById(request.plantId());
            if(optionalPlant.isEmpty()) {
                throw new CustomException(ErrorType.NOT_FOUND_EXCEPTION, "Растение не найдено");
            }
            Plant plant = optionalPlant.get();
            plant.addPet(pet);
        }

        jpaPetRepository.save(pet);
        return new PostPetResponse(
                pet.getId(),
                pet.getName(),
                pet.getUserAccount().getId(),
                Optional.ofNullable(pet.getPlant()).map(Plant::getId).orElse(null),
                Optional.ofNullable(pet.getDevice()).map(Device::getId).orElse(null)
        );
    }

    @Override
    @Transactional
    public PostPetResponse changePet(String username, Long petId, PostPetRequest request) {
        Optional<UserAccount> optionalUserAccount = jpaUserAccountRepository.findByUsername(username);
        if(optionalUserAccount.isEmpty()) {
            throw new CustomException(ErrorType.NOT_FOUND_EXCEPTION, "Пользователь не найден");
        }

        UserAccount userAccount = optionalUserAccount.get();
        List<Pet> pets = userAccount.getPets().stream()
                .filter(pet -> pet.getId().equals(petId))
                .toList();
        if(pets.isEmpty()) {
            throw new CustomException(ErrorType.NOT_FOUND_EXCEPTION, "Питомец не найден");
        }

        Pet pet = pets.getFirst();
        String oldName = pet.getName();
        String newName = request.petName();
        if(!oldName.equals(newName)) {
            pets = userAccount.getPets().stream()
                    .filter(pet1 -> pet1.getName().equals(newName))
                    .toList();
            if(!pets.isEmpty()) {
                throw new CustomException(ErrorType.ALREADY_EXIST_EXCEPTION, "Питомец с таким именем уже существует");
            }
        }

        pet.setName(request.petName());

        if(request.deviceId() == null) {
            if(pet.getDevice() != null) {
                pet.getDevice().removePet(pet);
            }
        } else {
            Optional<Device> optionalDevice = jpaDeviceRepository.findById(request.deviceId());
            if(optionalDevice.isEmpty()) {
                throw new CustomException(ErrorType.NOT_FOUND_EXCEPTION, "Устройство не найдено");
            } else {
                Device device = optionalDevice.get();
                if(device.getPet() != null && device.getPet() != pet) {
                    throw new CustomException(ErrorType.ALREADY_EXIST_EXCEPTION, "Устройство уже привязано к другому питомцу");
                }
                if(pet.getDevice() != null) {
                    pet.getDevice().removePet(pet);
                }
                device.addPet(pet);
            }
        }

        if(request.plantId() == null) {
            if(pet.getPlant() != null) {
                pet.getPlant().removePet(pet);
            }
        } else {
            Optional<Plant> optionalPlant = jpaPlantRepository.findById(request.plantId());
            if(optionalPlant.isEmpty()) {
                throw new CustomException(ErrorType.NOT_FOUND_EXCEPTION, "Растение не найдено");
            } else {
                if(pet.getPlant() != null) {
                    pet.getPlant().removePet(pet);
                }
                optionalPlant.get().addPet(pet);
            }
        }

        jpaPetRepository.save(pet);
        return new PostPetResponse(
                pet.getId(),
                pet.getName(),
                pet.getUserAccount().getId(),
                Optional.ofNullable(pet.getPlant()).map(Plant::getId).orElse(null),
                Optional.ofNullable(pet.getDevice()).map(Device::getId).orElse(null)
        );
    }

    @Override
    @Transactional
    public DeletePetResponse deletePet(String username, Long petId) {
        Optional<UserAccount> optionalUserAccount = jpaUserAccountRepository.findByUsername(username);
        if(optionalUserAccount.isEmpty()) {
            throw new CustomException(ErrorType.NOT_FOUND_EXCEPTION, "Пользователь не найден");
        }

        UserAccount userAccount = optionalUserAccount.get();
        List<Pet> pets = userAccount.getPets().stream()
                .filter(pet -> pet.getId().equals(petId))
                .toList();
        if(pets.isEmpty()) {
            throw new CustomException(ErrorType.NOT_FOUND_EXCEPTION, "Питомец не найден");
        }

        Pet pet = pets.getFirst();
        DeletePetResponse response = new DeletePetResponse(
                pet.getId(),
                pet.getName(),
                pet.getUserAccount().getId(),
                Optional.ofNullable(pet.getPlant()).map(Plant::getId).orElse(null),
                Optional.ofNullable(pet.getDevice()).map(Device::getId).orElse(null)
        );

        pet.getUserAccount().removePet(pet);
        if(pet.getPlant() != null) pet.getPlant().removePet(pet);
        if(pet.getDevice() != null) pet.getDevice().removePet(pet);
        jpaPetRepository.delete(pet);
        return response;
    }

    @Override
    public GetAllMeasuresResponse getMeasures(String username, Long petId, Integer size) {
        Optional<UserAccount> optionalUserAccount = jpaUserAccountRepository.findByUsername(username);
        if(optionalUserAccount.isEmpty()) {
            throw new CustomException(ErrorType.NOT_FOUND_EXCEPTION, "Пользователь не найден");
        }

        UserAccount userAccount = optionalUserAccount.get();
        List<Pet> pets = userAccount.getPets().stream()
                .filter(pet -> pet.getId().equals(petId))
                .toList();
        if(pets.isEmpty()) {
            throw new CustomException(ErrorType.NOT_FOUND_EXCEPTION, "Питомец не найден");
        }
        Pet pet = pets.getFirst();
        Optional<Device> optionalDevice = Optional.ofNullable(pet.getDevice());
        GetAllMeasuresResponse measuresResponse = getAllMeasuresResponse(optionalDevice, size);
        return Objects.requireNonNullElseGet(measuresResponse, () -> new GetAllMeasuresResponse(new ArrayList<>()));
    }

    @Override
    public GetAllSpecificTypeMeasuresResponse getSpecificTypeMeasures(String username, Long petId,
                                                                      Integer size, MeasureType measureType) {
        Optional<UserAccount> optionalUserAccount = jpaUserAccountRepository.findByUsername(username);
        if(optionalUserAccount.isEmpty()) {
            throw new CustomException(ErrorType.NOT_FOUND_EXCEPTION, "Пользователь не найден");
        }

        UserAccount userAccount = optionalUserAccount.get();
        List<Pet> pets = userAccount.getPets().stream()
                .filter(pet -> pet.getId().equals(petId))
                .toList();
        if(pets.isEmpty()) {
            throw new CustomException(ErrorType.NOT_FOUND_EXCEPTION, "Питомец не найден");
        }
        Pet pet = pets.getFirst();
        Optional<Device> optionalDevice = Optional.ofNullable(pet.getDevice());
        GetAllSpecificTypeMeasuresResponse measuresResponse = getAllSpecificTypeMeasuresResponse(optionalDevice, measureType, size);
        return Objects.requireNonNullElseGet(measuresResponse, () -> new GetAllSpecificTypeMeasuresResponse(new ArrayList<>()));
    }

    @Override
    public GetFullPetInfo getPet(String username, Long petId) {
        Optional<UserAccount> optionalUserAccount = jpaUserAccountRepository.findByUsername(username);
        if(optionalUserAccount.isEmpty()){
            throw new CustomException(ErrorType.NOT_FOUND_EXCEPTION, "Пользователь не найден");
        } else {
            UserAccount userAccount = optionalUserAccount.get();
            List<Pet> pets = userAccount.getPets().stream()
                    .filter(pet -> pet.getId().equals(petId))
                    .toList();
            if(pets.isEmpty()) {
                throw new CustomException(ErrorType.NOT_FOUND_EXCEPTION, "Питомец не найден");
            } else {
                Pet pet = pets.getFirst();
                Optional<Plant> plant = Optional.ofNullable(pet.getPlant());
                Optional<Device> device = Optional.ofNullable(pet.getDevice());
                GetAllMeasuresResponse allMeasuresResponse = getAllMeasuresResponse(device, sizeOfMeasuresList);
                GetMeasureResponse measureResponse;
                if(allMeasuresResponse == null) {
                    measureResponse = null;
                } else {
                    measureResponse = allMeasuresResponse.measures().getLast();
                }
                return new GetFullPetInfo(
                        pet.getId(),
                        pet.getName(),
                        plant.map(Plant::getId).orElse(null),
                        plant.map(Plant::getName).orElse(null),
                        device.map(Device::getId).orElse(null),
                        measureResponse
                );
            }
        }
    }

    private GetAllMeasuresResponse getAllMeasuresResponse(Optional<Device> device, Integer size) {
        if(device.isPresent() && !device.get().getMeasures().isEmpty()) {
            if (device.get().getMeasures().size() < size) size = device.get().getMeasures().size();
            List<GetMeasureResponse> measureResponses = device.get().getMeasures()
                    .reversed()
                    .subList(0, size)
                    .stream()
                    .map(measure -> new GetMeasureResponse(
                            measure.getId(),
                            measure.getDevice().getId(),
                            measure.getCheckTime(),
                            measure.getLightLux(),
                            measure.getTemp(),
                            measure.getEnvHumid(),
                            measure.getSoilMoist(),
                            measure.getBatteryLevel()
                    ))
                    .toList()
                    .reversed();
            return new GetAllMeasuresResponse(measureResponses);
        } else {
            return null;
        }
    }

    private GetAllSpecificTypeMeasuresResponse getAllSpecificTypeMeasuresResponse(Optional<Device> device,
                                                                                  MeasureType measureType,
                                                                                  Integer size) {
        if(device.isPresent() && !device.get().getMeasures().isEmpty()) {
            if (device.get().getMeasures().size() < size) size = device.get().getMeasures().size();
            List<GetSpecificTypeMeasureResponse> measureResponses = device.get().getMeasures()
                    .reversed()
                    .subList(0, size)
                    .stream()
                    .map(measure -> {
                        Integer measureValue = null;
                        switch (measureType) {
                            case TEMPERATURE -> measureValue = measure.getTemp();
                            case LIGHT -> measureValue = measure.getLightLux();
                            case MOISTURE -> measureValue = measure.getSoilMoist();
                            case HUMIDITY -> measureValue = measure.getEnvHumid();
                            case BATTERY -> measureValue = measure.getBatteryLevel().intValue();
                        }
                        return new GetSpecificTypeMeasureResponse(
                                measure.getId(),
                                measure.getDevice().getId(),
                                measure.getCheckTime(),
                                measureValue
                        );
                    })
                    .toList()
                    .reversed();
            return new GetAllSpecificTypeMeasuresResponse(measureResponses);
        } else {
            return null;
        }
    }

    @Override
    public GetPetsResponse getAllPets(String username) {
        Optional<UserAccount> optionalUserAccount = jpaUserAccountRepository.findByUsername(username);
        if(optionalUserAccount.isEmpty()){
            throw new CustomException(ErrorType.NOT_FOUND_EXCEPTION, "Пользователь не найден");
        } else {
            UserAccount userAccount = optionalUserAccount.get();
            List<Pet> pets = userAccount.getPets();
            List<PetResponse> petResponses = pets.stream()
                    .map(pet -> {
                        Optional<Plant> plant = Optional.ofNullable(pet.getPlant());
                        return new PetResponse(
                                pet.getId(),
                                pet.getName(),
                                plant.map(Plant::getName).orElse(null),
                                null,
                                determinePetCondition(pet)
                        );
                    })
                    .toList();
            return new GetPetsResponse(petResponses);
        }
    }

    private String determinePetCondition(Pet pet) {
        Device device = pet.getDevice();
        Plant plant = pet.getPlant();
        if (plant == null || device == null || device.getMeasures().isEmpty()) {
            return OK_CONDITION;
        }
        Measure lastMeasure = device.getMeasures().getLast();
        if (
                isWithinRange(lastMeasure.getLightLux(), plant.getMinLightLux(), plant.getMaxLightLux()) &&
                isWithinRange(lastMeasure.getTemp(), plant.getMinTemp(), plant.getMaxTemp()) &&
                isWithinRange(lastMeasure.getEnvHumid(), plant.getMinEnvHumid(), plant.getMaxEnvHumid()) &&
                isWithinRange(lastMeasure.getSoilMoist(), plant.getMinSoilMoist(), plant.getMaxSoilMoist())
        ) {
            return HAPPY_CONDITION;
        }

        if (
                isSlightlyOutOfRange(lastMeasure.getLightLux(), plant.getMinLightLux(), plant.getMaxLightLux()) &&
                isSlightlyOutOfRange(lastMeasure.getTemp(), plant.getMinTemp(), plant.getMaxTemp()) &&
                isSlightlyOutOfRange(lastMeasure.getEnvHumid(), plant.getMinEnvHumid(), plant.getMaxEnvHumid()) &&
                isSlightlyOutOfRange(lastMeasure.getSoilMoist(), plant.getMinSoilMoist(), plant.getMaxSoilMoist())
        ) {
            return OK_CONDITION;
        }

        return SAD_CONDITION;
    }

    private boolean isWithinRange(int value, int min, int max) {
        return value >= min && value <= max;
    }

    private boolean isSlightlyOutOfRange(int value, int min, int max) {
        int lowerBound = min * (100 - deviation) / 100;
        int upperBound = max * (100 + deviation) / 100;
        return value >= lowerBound && value <= upperBound;
    }
}
