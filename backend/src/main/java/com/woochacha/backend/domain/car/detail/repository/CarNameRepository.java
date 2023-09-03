package com.woochacha.backend.domain.car.detail.repository;

import com.woochacha.backend.domain.car.detail.entity.CarName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarNameRepository extends JpaRepository<CarName, Long> {

    CarName findByName(String name);
}
