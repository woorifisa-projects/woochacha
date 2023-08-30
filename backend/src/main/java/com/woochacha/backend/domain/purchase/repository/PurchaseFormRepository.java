package com.woochacha.backend.domain.purchase.repository;

import com.woochacha.backend.domain.purchase.entity.PurchaseForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PurchaseFormRepository extends JpaRepository<PurchaseForm, Long> {

}
