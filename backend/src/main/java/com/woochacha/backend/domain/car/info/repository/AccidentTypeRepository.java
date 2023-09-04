package com.woochacha.backend.domain.car.info.repository;

import com.woochacha.backend.domain.car.info.entity.AccidentType;
import com.woochacha.backend.domain.car.info.entity.AccidentTypeList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccidentTypeRepository extends JpaRepository<AccidentType, Short> {
    AccidentType findIdByType(AccidentTypeList typeName);
}
