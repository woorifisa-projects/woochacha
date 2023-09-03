package com.woochacha.backend.domain.purchase.repository;

import com.woochacha.backend.domain.admin.dto.manageMember.PurchaseMemberInfoResponseDto;
import com.woochacha.backend.domain.purchase.entity.PurchaseForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;


@Repository
public interface PurchaseFormRepository extends JpaRepository<PurchaseForm, Long> {
    @Query("SELECT pf.product.id, pf.product.carDetail.carNum, pf.member.name, pf.product.saleForm.member.name, pf.status, pf.id, pf.product.saleForm.id " +
            "FROM PurchaseForm pf " +
            "JOIN pf.product p " +
            "JOIN p.saleForm sf " +
            "JOIN sf.member m " +
            "JOIN p.carDetail cd")
    Page<Object[]> findAllPurchaseFormInfo(Pageable pageable);

    @Query("SELECT new com.woochacha.backend.domain.admin.dto.manageMember.PurchaseMemberInfoResponseDto(" +
            "pf.product.saleForm.member.name, pf.product.saleForm.member.phone, pf.member.name, pf.member.phone) " +
            "FROM PurchaseForm pf " +
            "JOIN pf.product p " +
            "JOIN p.saleForm sf " +
            "JOIN sf.member m " +
            "WHERE pf.id = :purchaseId")
    PurchaseMemberInfoResponseDto findPurchaseMemberInfo(@Param("purchaseId") Long purchaseId);


    @Modifying
    @Query("UPDATE PurchaseForm AS pf SET pf.meetingDate = :meetingDate, pf.status = 0 WHERE pf.id = :purchaseFormId")
    void updatePurchaseDateANDStatus(@Param("purchaseFormId") Long purchaseFormId, @Param("meetingDate") LocalDate meetingDate);

    @Query("SELECT pf.product.saleForm.id " +
            "FROM PurchaseForm pf " +
            "JOIN pf.product p " +
            "JOIN p.saleForm sf " +
            "WHERE pf.id = :purchaseId")
    Long getSaleFormId(@Param("purchaseId") Long purchaseId);
}
