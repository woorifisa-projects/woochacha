package com.woochacha.backend.domain.admin.controller;

import com.amazon.ion.IonInt;
import com.woochacha.backend.domain.admin.dto.ApproveSaleResponseDto;
import com.woochacha.backend.domain.admin.dto.CarInspectionInfoResponseDto;
import com.woochacha.backend.domain.admin.dto.CompareRequestDto;
import com.woochacha.backend.domain.admin.service.ApproveSaleService;
import com.woochacha.backend.domain.qldb.service.QldbService;
import com.woochacha.backend.domain.sale.repository.SaleFormRepository;
import com.woochacha.backend.domain.sale.service.SaleFormApplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import software.amazon.qldb.Result;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/sales")
public class ApproveSaleController {

    private final ApproveSaleService approveSaleService;
    private final QldbService qldbService;
    private final SaleFormApplyService saleFormApplyService;

    //url 뒷단에 ?page=?&size=?로 값을 정해서 보내줘야한다.
    @GetMapping
    public ResponseEntity<Page<ApproveSaleResponseDto>> allSaleForms(Pageable pageable) {
        List<ApproveSaleResponseDto> saleForms = approveSaleService.getApproveSaleForm(pageable).getResults();
        return ResponseEntity.ok(new PageImpl<>(saleForms, pageable, saleForms.size()));
    }

    @GetMapping("/approve/{saleFormId}")
    public ResponseEntity<CarInspectionInfoResponseDto> qldbCarInfo(@PathVariable Long saleFormId){
        String carNum = saleFormApplyService.findCarNum(saleFormId);
        String accidentMetaId = qldbService.getMetaIdValue(carNum, "car_accident");
        String exchangeMetaId = qldbService.getMetaIdValue(carNum, "car_exchange");
        CarInspectionInfoResponseDto carResponseInfo = approveSaleService.getQldbCarInfoList(carNum,accidentMetaId,exchangeMetaId);
        return ResponseEntity.ok(carResponseInfo);
    }

    @PostMapping("/approve/{saleFormId}")
    public ResponseEntity<Boolean> compareCarInfo(@RequestBody CompareRequestDto compareRequestDto, @PathVariable Long saleFormId){
        String carNum = saleFormApplyService.findCarNum(saleFormId);
        int carDistance = approveSaleService.getCarDistance(carNum);
        if(carDistance > compareRequestDto.getDistance()){
            return ResponseEntity.ok(false);
        }else {
            approveSaleService.updateSaleFormStatus(compareRequestDto.getSaleFormId());
            return ResponseEntity.ok(true);
        }
    }
}
