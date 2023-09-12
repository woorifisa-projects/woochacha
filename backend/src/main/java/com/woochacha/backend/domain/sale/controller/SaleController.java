package com.woochacha.backend.domain.sale.controller;


import com.woochacha.backend.domain.sale.dto.BranchDto;
import com.woochacha.backend.domain.sale.dto.SaleFormRequestDto;
import com.woochacha.backend.domain.sale.service.SaleFormApplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products/sale")
public class SaleController {
    private final SaleFormApplyService saleFormApplyService;

    @GetMapping
    public ResponseEntity<List<BranchDto>> carSaleForm() {
        List<BranchDto> branchList = saleFormApplyService.getBranchList();
        return ResponseEntity.ok(branchList);
    }

    @PostMapping
    public ResponseEntity<String> submitCarSaleForm(@RequestBody SaleFormRequestDto requestDto) {
        String carNum = requestDto.getCarNum();
        Long memberId = requestDto.getMemberId();
        Long branchId = requestDto.getBranchId();
        String match = saleFormApplyService.submitCarSaleForm(carNum, memberId, branchId);
        return ResponseEntity.ok(match);
        // 둘 중 return 값으로 어떤 걸 사용할지 고민 중
        // return new ResponseEntity<>(saleForm, HttpStatus.CREATED);
    }
}
