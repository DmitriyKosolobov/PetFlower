package ru.petflower.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.petflower.domain.entity.UserAccountInfo;

@Repository
public interface JpaUserAccountInfoRepository extends JpaRepository<UserAccountInfo, Long> {
}
