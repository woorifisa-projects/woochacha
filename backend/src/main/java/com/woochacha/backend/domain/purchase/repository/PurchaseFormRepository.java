package com.woochacha.backend.domain.purchase.repository;

import com.woochacha.backend.domain.purchase.entity.PurchaseForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PurchaseFormRepository extends JpaRepository<PurchaseForm, Long> {
    @Query("SELECT pf.product.id, pf.product.carDetail.carNum, pf.member.name, pf.product.saleForm.member.name, pf.status, pf.id, pf.product.saleForm.id " +
            "FROM PurchaseForm pf " +
            "JOIN pf.product p " +
            "JOIN p.saleForm sf " +
            "JOIN sf.member m " +
            "JOIN p.carDetail cd")
    Page<Object[]> findAllPurchaseFormInfo(Pageable pageable);
}
