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

    @Modifying
    @Query("UPDATE SaleForm AS sf SET sf.carStatus = 1 WHERE sf.id = :saleFormId")
    int updateDenyStatus(@Param("saleFormId") Long saleFormId);

    @Query("SELECT COUNT(*) FROM SaleForm sf WHERE sf.member.id = :memberId AND sf.carStatus.id = :carStatusId")
    int countSale(@Param("memberId") Long memberId, @Param("carStatusId") short carStatusId);

    @Query("SELECT COUNT(*) FROM SaleForm sf WHERE sf.member.id = :memberId AND sf.carNum = :carNum")
    int registeredCarNumLen(@Param("memberId") Long memberId, @Param("carNum") String carNum);

}
