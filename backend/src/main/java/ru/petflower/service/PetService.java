package ru.petflower.service;

import ru.petflower.domain.entity.Pet;

import java.util.List;
import java.util.Optional;

public interface PetService {
    void addPet();
    void changePet();
    void deletePet();
    Optional<Pet> getPet();
    List<Pet> getAllPets();
}
