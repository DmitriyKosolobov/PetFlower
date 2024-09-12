package ru.petflower.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.petflower.domain.entity.Plant;

import java.util.Optional;

@Repository
public interface JpaPlantRepository extends JpaRepository<Plant, Long> {
    Optional<Plant> findByName(String name);
}
