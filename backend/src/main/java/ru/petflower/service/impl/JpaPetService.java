package ru.petflower.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.petflower.controller.requests.pet.PostPetRequest;
import ru.petflower.controller.responses.device.GetAllMeasuresResponse;
import ru.petflower.controller.responses.device.GetMeasureResponse;
import ru.petflower.controller.responses.pet.*;
import ru.petflower.domain.JpaDeviceRepository;
import ru.petflower.domain.JpaPetRepository;
import ru.petflower.domain.JpaPlantRepository;
import ru.petflower.domain.JpaUserAccountRepository;
import ru.petflower.domain.entity.*;
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
    public GetAllMeasuresResponse getMeasures(String username, Long petId) {
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
        GetAllMeasuresResponse measuresResponse = getAllMeasuresResponse(optionalDevice);
        return Objects.requireNonNullElseGet(measuresResponse, () -> new GetAllMeasuresResponse(new ArrayList<>()));
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
                GetAllMeasuresResponse allMeasuresResponse = getAllMeasuresResponse(device);
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

    private GetAllMeasuresResponse getAllMeasuresResponse(Optional<Device> device) {
        if(device.isPresent() && !device.get().getMeasures().isEmpty()) {
            List<GetMeasureResponse> measureResponses = device.get().getMeasures()
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
                    .toList();
            return new GetAllMeasuresResponse(measureResponses);
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
                                null
                        );
                    })
                    .toList();
            return new GetPetsResponse(petResponses);
        }
    }
}
