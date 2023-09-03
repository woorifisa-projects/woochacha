package com.woochacha.backend.domain.car.type.repository;

import com.woochacha.backend.domain.car.type.entity.Color;
import com.woochacha.backend.domain.car.type.entity.ColorList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColorRepository extends JpaRepository<Color, Integer> {
    Color findByName(ColorList name);
}
