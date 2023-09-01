package com.woochacha.backend.domain.transaction.repository;

import com.woochacha.backend.domain.transaction.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("SELECT t FROM Transaction t WHERE t.saleForm.id = :saleId AND t.purchaseForm.id = :purchaseId")
    Transaction findBySaleIdAndPurchaseId(@Param("saleId") Long saleId, @Param("purchaseId") Long purchaseId);
}
