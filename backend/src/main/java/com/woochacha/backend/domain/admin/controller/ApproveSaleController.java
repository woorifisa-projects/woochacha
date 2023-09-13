package com.woochacha.backend.domain.admin.controller;

import com.woochacha.backend.domain.admin.dto.RegisterInputDto;
import com.woochacha.backend.domain.admin.dto.RegisterProductDto;
import com.woochacha.backend.domain.admin.dto.approve.ApproveSaleResponseDto;
import com.woochacha.backend.domain.admin.dto.approve.CarInspectionInfoResponseDto;
import com.woochacha.backend.domain.admin.dto.approve.CompareRequestDto;
import com.woochacha.backend.domain.admin.service.ApproveSaleService;
import com.woochacha.backend.domain.admin.service.RegisterProductService;
import com.woochacha.backend.domain.qldb.service.QldbService;
import com.woochacha.backend.domain.sale.service.SaleFormApplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/sales")
public class ApproveSaleController{

    private final ApproveSaleService approveSaleService;
    private final QldbService qldbService;
    private final SaleFormApplyService saleFormApplyService;
    private final RegisterProductService registerProductService;

    //url 뒷단에 ?page=?&size=?로 값을 정해서 보내줘야한다.
    //관리자 페이지에서 모든 차량의 saleform 신청폼(점검 전)을 확인한다.
    @GetMapping
    public ResponseEntity<Page<ApproveSaleResponseDto>> allSaleForms(Pageable pageable) {
        return ResponseEntity.ok(approveSaleService.getApproveSaleForm(pageable));
    }

    // 점검 후 해당 매물이 판매 불가 제품일 때 반려를 한다.
    @PatchMapping("/deny/{saleFormId}")
    public ResponseEntity<Boolean> denySaleForm(@PathVariable Long saleFormId){
        return ResponseEntity.ok(approveSaleService.updateSaleFormDenyStatus(saleFormId));
    }

    // 점검 후 해당 saleform에 해당하는 car의 정보와 distance,car accident, car exchange를 조회한다.
    @GetMapping("/approve/{saleFormId}")
    public ResponseEntity<CarInspectionInfoResponseDto> qldbCarInfo(@PathVariable Long saleFormId){
        CarInspectionInfoResponseDto carResponseInfo = approveSaleService.getQldbCarInfoList(saleFormId);
        return ResponseEntity.ok(carResponseInfo);
    }

    // 차량이 점검 후 입력한 값이 등록 조건에 맞으면 saleForm의 status를 변환시킨다.
    @PatchMapping("/approve/{saleFormId}")
    @Transactional
    public ResponseEntity<Boolean> compareCarInfo(@RequestBody CompareRequestDto compareRequestDto, @PathVariable Long saleFormId){
        Boolean result = approveSaleService.compareCarHistory(compareRequestDto,saleFormId);
        return ResponseEntity.ok(result);
    }

    // 차량 게시글 등록을위한 폼 데이터를 QLDB에서 조회한다.
    @GetMapping("/register/{saleFormId}")
    public ResponseEntity<RegisterProductDto> registerProductInfo(@PathVariable("saleFormId") Long saleFormId){
        RegisterProductDto registerProductDto = registerProductService.getRegisterProductInfo(saleFormId);
        return ResponseEntity.ok(registerProductDto);
    }

    // 차량 게시글을 등록하는 Post요청
    @PostMapping("/register/{saleFormId}")
    public ResponseEntity<String> registerProduct(@PathVariable("saleFormId") Long saleFormId, @ModelAttribute RegisterInputDto registerInputDto) throws IOException, ParseException {
        registerProductService.registerProduct(saleFormId, registerInputDto);
        return ResponseEntity.ok("Success");
    }
}
