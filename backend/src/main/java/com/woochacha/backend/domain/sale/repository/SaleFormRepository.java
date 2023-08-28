package com.woochacha.backend.domain.sale.repository;

import com.woochacha.backend.domain.sale.entity.SaleForm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleFormRepository extends JpaRepository<SaleForm, Long> {
}
