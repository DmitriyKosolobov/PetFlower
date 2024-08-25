package ru.petflower.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.petflower.domain.entity.Device;

import java.util.Optional;

@Repository
public interface JpaDeviceRepository extends JpaRepository<Device, Long> {
    Optional<Device> findByKey(String key);
}
