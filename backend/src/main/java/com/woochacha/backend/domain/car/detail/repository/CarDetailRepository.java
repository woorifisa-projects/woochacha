package com.woochacha.backend.domain.car.detail.repository;

import com.woochacha.backend.domain.car.detail.entity.CarDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarDetailRepository extends JpaRepository<CarDetail, String> {
}
