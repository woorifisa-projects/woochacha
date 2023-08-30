package com.woochacha.backend.domain.sale.controller;


import com.woochacha.backend.domain.member.repository.MemberRepository;
import com.woochacha.backend.domain.sale.dto.SaleFormRequestDto;
import com.woochacha.backend.domain.sale.entity.Branch;
import com.woochacha.backend.domain.sale.repository.BranchRepository;
import com.woochacha.backend.domain.sale.service.SaleFormApplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products/sale")
public class SaleController {
    private final MemberRepository memberRepository;
    private final BranchRepository branchRepository;
    private final SaleFormApplyService saleFormApplyService;

    @GetMapping
    public ResponseEntity<List<Branch>> carSaleForm() {
        List<Branch> branchList = saleFormApplyService.getBranchList();
        return ResponseEntity.ok(branchList);
    }

    @PostMapping
    public ResponseEntity<Boolean> submitCarSaleForm(@RequestBody SaleFormRequestDto requestDto) {
        String carNum = requestDto.getCarNum();
        Long memberId = requestDto.getMemberId();
        Long branchId = requestDto.getBranchId();

        Boolean match = saleFormApplyService.submitCarSaleForm(carNum, memberId);

        if(match){
            saleFormApplyService.saveSaleForm(carNum, memberId, branchId);
            return ResponseEntity.ok(true);
        }else{
            return ResponseEntity.ok(false);
        }
        // 둘 중 return 값으로 어떤 걸 사용할지 고민 중
        // return new ResponseEntity<>(saleForm, HttpStatus.CREATED);
    }
}
