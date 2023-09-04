package com.woochacha.backend.domain.car.detail.repository;

import com.woochacha.backend.domain.car.detail.entity.CarOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarOptionRepository extends JpaRepository<CarOption, Long> {
}
