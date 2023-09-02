package com.woochacha.backend.domain.admin.service;

import com.querydsl.core.QueryResults;
import com.woochacha.backend.domain.admin.dto.approve.ApproveSaleResponseDto;
import com.woochacha.backend.domain.admin.dto.approve.CarAccidentInfoDto;
import com.woochacha.backend.domain.admin.dto.approve.CarExchangeInfoDto;
import com.woochacha.backend.domain.admin.dto.approve.CarInspectionInfoResponseDto;
import org.springframework.data.domain.Pageable;

public interface ApproveSaleService {
    QueryResults<ApproveSaleResponseDto> getApproveSaleForm(Pageable pageable);
    CarInspectionInfoResponseDto getQldbCarInfoList(String carNum, String accidentMetaId, String exchangeMetaId);
    int getCarDistance(String carNum);
    void updateSaleFormStatus(Long saleFormId);
    void updateQldbCarDistance(int carDistance, Long saleFormId);
    void updateQldbAccidentInfo(CarAccidentInfoDto carAccidentInfoDto, Long saleFormId);
    void updateQldbExchangeInfo(CarExchangeInfoDto carExchangeInfoDto, Long saleFormId);
}
