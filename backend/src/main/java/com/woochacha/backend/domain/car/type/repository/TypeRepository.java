package com.woochacha.backend.domain.car.type.repository;

import com.woochacha.backend.domain.car.type.entity.Type;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeRepository extends JpaRepository<Type, Integer> {
}
