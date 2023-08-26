package com.woochacha.backend.domain.sale.repository;

import com.woochacha.backend.domain.sale.entity.SaleForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleFormRepository extends JpaRepository<SaleForm, Long> {
    Page<SaleForm> findAllById(Long memberId, Pageable pageable);
}
