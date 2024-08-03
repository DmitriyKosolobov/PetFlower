package ru.petflower.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.petflower.domain.entity.Measure;

@Repository
public interface JpaMeasureRepository extends JpaRepository<Measure, Long> {
}
