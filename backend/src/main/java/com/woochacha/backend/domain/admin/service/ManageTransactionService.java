package com.woochacha.backend.domain.admin.service;

import com.woochacha.backend.domain.admin.dto.manageMember.PurchaseFormListResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ManageTransactionService {
    Page<PurchaseFormListResponseDto> findAllPurchaseFormInfo(Pageable pageable);
}
