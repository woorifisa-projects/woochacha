package com.woochacha.backend.domain.admin.service;

import com.querydsl.core.QueryResults;
import com.woochacha.backend.domain.admin.dto.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ApproveSaleService {
    QueryResults<ApproveSaleResponseDto> getApproveSaleForm(Pageable pageable);
    CarInspectionInfoResponseDto getQldbCarInfoList(String carNum, String accidentMetaId, String exchangeMetaId);
    int getCarDistance(String carNum);
    void updateSaleFormStatus(Long saleFormId);
    void updateQldbCarDistance(int carDistance, Long saleFormId);
    void updateQldbAccidentInfo(CarAccidentInfoDto carAccidentInfoDto, Long saleFormId);
    void updateQldbExchangeInfo(CarExchangeInfoDto carExchangeInfoDto, Long saleFormId);
}
