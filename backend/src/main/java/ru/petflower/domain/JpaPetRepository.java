package ru.petflower.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.petflower.domain.entity.Pet;

import java.util.Optional;

@Repository
public interface JpaPetRepository extends JpaRepository<Pet, Long> {
    Optional<Pet> findByName(String name);
}
