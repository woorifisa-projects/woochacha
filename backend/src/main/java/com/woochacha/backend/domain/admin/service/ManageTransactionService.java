package com.woochacha.backend.domain.admin.service;

import com.woochacha.backend.domain.admin.dto.manageMember.PurchaseDateRequestDto;
import com.woochacha.backend.domain.admin.dto.manageMember.PurchaseFormListResponseDto;
import com.woochacha.backend.domain.admin.dto.manageMember.PurchaseMemberInfoResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ManageTransactionService {
    Page<PurchaseFormListResponseDto> getAllPurchaseFormInfo(Pageable pageable);
    PurchaseMemberInfoResponseDto getPurchaseMemberInfo(Long purchaseId);
    String matchPurchaseDate(Long purchaseId, PurchaseDateRequestDto purchaseDateRequestDto);
}
