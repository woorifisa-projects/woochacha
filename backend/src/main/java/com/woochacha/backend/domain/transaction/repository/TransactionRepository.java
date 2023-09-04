package com.woochacha.backend.domain.transaction.repository;

import com.woochacha.backend.domain.transaction.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("SELECT t FROM Transaction t WHERE t.saleForm.id = :saleId AND t.purchaseForm.id = :purchaseId")
    Transaction findBySaleIdAndPurchaseId(@Param("saleId") Long saleId, @Param("purchaseId") Long purchaseId);

    @Query("SELECT COUNT(t) FROM Transaction t " +
            "JOIN t.purchaseForm pf " +
            "JOIN pf.member m WHERE t.purchaseForm.member.id = :memberId")
    int countCompletePurchase(@Param("memberId") Long memberId);

    @Modifying
    @Query(value = "INSERT INTO transaction (sale_id, purchase_id) VALUES (:saleFormId, :purchaseFormId)", nativeQuery = true)
    void insertTransaction(@Param("saleFormId") Long saleFormId, @Param("purchaseFormId") Long purchaseFormId);
}
