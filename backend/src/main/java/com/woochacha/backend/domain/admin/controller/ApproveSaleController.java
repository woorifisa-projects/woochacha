package com.woochacha.backend.domain.admin.controller;

import com.woochacha.backend.domain.admin.dto.ApproveSaleResponseDto;
import com.woochacha.backend.domain.admin.dto.CarInspectionInfoResponseDto;
import com.woochacha.backend.domain.admin.service.ApproveSaleService;
import com.woochacha.backend.domain.qldb.service.QldbService;
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
    private final QldbService qldbService;

    @GetMapping
    public ResponseEntity<Page<ApproveSaleResponseDto>> allSaleForms(Pageable pageable) {
        List<ApproveSaleResponseDto> saleForms = approveSaleService.getApproveSaleForm(pageable).getResults();
        return ResponseEntity.ok(new PageImpl<>(saleForms, pageable, saleForms.size()));
    }

    @GetMapping("/approve/{carNum}")
    public ResponseEntity<CarInspectionInfoResponseDto> qldbCarInfo(@PathVariable String carNum){
        String accidentMetaId = qldbService.getMetaIdValue(carNum, "car_accident");
        String exchangeMetaId = qldbService.getMetaIdValue(carNum, "car_exchange");
        CarInspectionInfoResponseDto carResponseInfo = approveSaleService.getQldbCarInfoList(carNum,accidentMetaId,exchangeMetaId);
        return ResponseEntity.ok(carResponseInfo);
    }

    @PostMapping("/approve/{carNum}")
    public ResponseEntity<Boolean> compareCarInfo(@RequestBody int distance, @PathVariable String carNum){
        int carDistance = qldbService.getCarDistance(carNum);
        if(carDistance > distance){
            return ResponseEntity.ok(false);
        }else {
            return ResponseEntity.ok(true);
        }
    }
}
