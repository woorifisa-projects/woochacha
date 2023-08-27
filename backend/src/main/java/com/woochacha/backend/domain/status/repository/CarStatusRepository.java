package com.woochacha.backend.domain.status.repository;

import com.woochacha.backend.domain.status.entity.CarStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarStatusRepository extends JpaRepository<CarStatus, Short> {
}
