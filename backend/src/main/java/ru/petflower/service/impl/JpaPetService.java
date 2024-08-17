package ru.petflower.service.impl;

import org.springframework.stereotype.Service;
import ru.petflower.domain.entity.Pet;
import ru.petflower.service.PetService;

import java.util.List;
import java.util.Optional;

@Service
public class JpaPetService implements PetService {

    @Override
    public void addPet() {

    }

    @Override
    public void changePet() {

    }

    @Override
    public void deletePet() {

    }

    @Override
    public Optional<Pet> getPet() {
        return Optional.empty();
    }

    @Override
    public List<Pet> getAllPets() {
        return null;
    }
}
