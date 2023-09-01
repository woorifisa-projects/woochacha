package com.woochacha.backend.domain.admin.controller;

import com.woochacha.backend.domain.admin.dto.manageMember.PurchaseFormListResponseDto;
import com.woochacha.backend.domain.admin.service.ManageTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/purchase")
public class ManageTransactionController {
    private final ManageTransactionService manageTransactionService;
    @GetMapping
    public ResponseEntity<Page<PurchaseFormListResponseDto>> getAllPurchaseList(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page,size);
        Page<PurchaseFormListResponseDto> purchaseFormListResponseDto = manageTransactionService.findAllPurchaseFormInfo(pageable);
        return ResponseEntity.ok(purchaseFormListResponseDto);
    }
}
