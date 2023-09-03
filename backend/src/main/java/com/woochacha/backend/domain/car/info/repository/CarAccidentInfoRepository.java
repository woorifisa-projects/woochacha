package com.woochacha.backend.domain.car.info.repository;

import com.woochacha.backend.domain.car.info.entity.CarAccidentInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarAccidentInfoRepository extends JpaRepository<CarAccidentInfo, Integer> {
}
