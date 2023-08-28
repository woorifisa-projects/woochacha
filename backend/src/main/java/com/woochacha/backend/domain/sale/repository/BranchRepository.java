package com.woochacha.backend.domain.sale.repository;

import com.woochacha.backend.domain.sale.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BranchRepository extends JpaRepository<Branch,Long> {
}
