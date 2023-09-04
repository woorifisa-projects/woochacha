package com.woochacha.backend.domain.admin.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.woochacha.backend.domain.admin.dto.manageMember.PurchaseDateRequestDto;
import com.woochacha.backend.domain.admin.dto.manageMember.PurchaseFormListResponseDto;
import com.woochacha.backend.domain.admin.dto.manageMember.PurchaseMemberInfoResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface ManageTransactionService {
    Page<PurchaseFormListResponseDto> getAllPurchaseFormInfo(Pageable pageable);
    PurchaseMemberInfoResponseDto getPurchaseMemberInfo(Long purchaseId);
    String matchPurchaseDate(Long purchaseId, PurchaseDateRequestDto purchaseDateRequestDto) throws UnsupportedEncodingException, NoSuchAlgorithmException, URISyntaxException, InvalidKeyException, JsonProcessingException;
    String insertNewTransaction(Long purchaseId);
}