package com.woochacha.backend.domain.car.type.repository;

import com.woochacha.backend.domain.car.type.entity.Fuel;
import com.woochacha.backend.domain.car.type.entity.FuelList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FuelRepository extends JpaRepository<Fuel, Integer> {
    Fuel findByName(String name);
}
