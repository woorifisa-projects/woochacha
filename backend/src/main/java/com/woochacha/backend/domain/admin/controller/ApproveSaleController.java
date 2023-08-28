package com.woochacha.backend.domain.admin.controller;

import com.woochacha.backend.domain.admin.dto.ApproveSaleResponseDto;
import com.woochacha.backend.domain.admin.dto.CarInspectionInfoDto;
import com.woochacha.backend.domain.admin.service.ApproveSaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/sales")
public class ApproveSaleController {

    private final ApproveSaleService approveSaleService;

    @GetMapping
    public ResponseEntity<Page<ApproveSaleResponseDto>> getSaleFormsInReview(Pageable pageable) {
        List<ApproveSaleResponseDto> saleForms = approveSaleService.getApproveSaleForm(pageable).getResults();
        return ResponseEntity.ok(new PageImpl<>(saleForms, pageable, saleForms.size()));
    }

    @PostMapping
    public ResponseEntity<Boolean> registerSaleForm(@RequestBody CarInspectionInfoDto carInspectionInfoDto){

        return ResponseEntity.ok(false);
    }
}
