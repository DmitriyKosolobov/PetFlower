package ru.petflower.service;

import ru.petflower.controller.requests.pet.PostPetRequest;
import ru.petflower.controller.responses.device.GetAllMeasuresResponse;
import ru.petflower.controller.responses.device.GetAllSpecificTypeMeasuresResponse;
import ru.petflower.controller.responses.pet.DeletePetResponse;
import ru.petflower.controller.responses.pet.GetFullPetInfo;
import ru.petflower.controller.responses.pet.GetPetsResponse;
import ru.petflower.controller.responses.pet.PostPetResponse;
import ru.petflower.enums.MeasureType;

public interface PetService {
    PostPetResponse addPet(String username, PostPetRequest postPetRequest);
    PostPetResponse changePet(String username, Long petId, PostPetRequest postPetRequest);
    DeletePetResponse deletePet(String username, Long petId);
    GetAllMeasuresResponse getMeasures(String username, Long petId, Integer size);
    GetAllSpecificTypeMeasuresResponse getSpecificTypeMeasures(String username, Long petId, Integer size, MeasureType measureType);
    GetFullPetInfo getPet(String username, Long petId);
    GetPetsResponse getAllPets(String username);
}
