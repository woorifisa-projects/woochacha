package com.woochacha.backend.domain.sale.repository;

import com.woochacha.backend.domain.sale.entity.SaleForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleFormRepository extends JpaRepository<SaleForm, Long> {
    @Modifying
    @Query("UPDATE SaleForm AS sf SET sf.carStatus = 3 WHERE sf.id = :saleFormId")
    void updateStatus(@Param("saleFormId") Long saleFormId);
}
