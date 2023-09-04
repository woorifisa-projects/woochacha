package com.woochacha.backend.domain.car.info.repository;

import com.woochacha.backend.domain.car.info.entity.ExchangeType;
import com.woochacha.backend.domain.car.info.entity.ExchangeTypeList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExchangeTypeRepository extends JpaRepository<ExchangeType, Short> {
    ExchangeType findIdByType(ExchangeTypeList typeName);
}
