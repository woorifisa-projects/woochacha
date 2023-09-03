package com.woochacha.backend.domain.admin.controller;

import com.woochacha.backend.domain.admin.dto.manageMember.PurchaseDateRequestDto;
import com.woochacha.backend.domain.admin.dto.manageMember.PurchaseMemberInfoResponseDto;
import com.woochacha.backend.domain.admin.dto.manageMember.PurchaseFormListResponseDto;
import com.woochacha.backend.domain.admin.service.ManageTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/purchase")
public class ManageTransactionController {
    private final ManageTransactionService manageTransactionService;
    @GetMapping
    public ResponseEntity<Page<PurchaseFormListResponseDto>> getAllPurchaseList(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page,size);
        Page<PurchaseFormListResponseDto> purchaseFormListResponseDto = manageTransactionService.getAllPurchaseFormInfo(pageable);
        return ResponseEntity.ok(purchaseFormListResponseDto);
    }
    @GetMapping("/{purchaseId}")
    public ResponseEntity<PurchaseMemberInfoResponseDto> matchPurchaseInfo(@PathVariable Long purchaseId){
        return ResponseEntity.ok(manageTransactionService.getPurchaseMemberInfo(purchaseId));
    }
    @PatchMapping ("/{purchaseId}")
    public ResponseEntity<String> matchPurchaseDate(@PathVariable Long purchaseId, @RequestBody PurchaseDateRequestDto purchaseDateRequestDto){
        return ResponseEntity.ok(manageTransactionService.matchPurchaseDate(purchaseId, purchaseDateRequestDto));
    }
}
