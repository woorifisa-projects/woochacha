package com.woochacha.backend.domain.car.info.repository;

import com.woochacha.backend.domain.car.info.entity.CarExchangeInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarExchangeInfoRepository extends JpaRepository<CarExchangeInfo, Integer> {
}
