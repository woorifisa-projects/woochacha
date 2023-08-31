package com.woochacha.backend.domain.car.type.repository;

import com.woochacha.backend.domain.car.type.entity.Transmission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransmissionRepository extends JpaRepository<Transmission, Integer> {
}
