package com.woochacha.backend.domain.car.type.repository;

import com.woochacha.backend.domain.car.type.entity.Model;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModelRepository extends JpaRepository<Model, Integer> {
}
