package com.woochacha.backend.domain.admin.service;

import com.querydsl.core.QueryResults;
import com.woochacha.backend.domain.admin.dto.ApproveSaleResponseDto;
import com.woochacha.backend.domain.admin.dto.CarInspectionInfoResponseDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ApproveSaleService {
    QueryResults<ApproveSaleResponseDto> getApproveSaleForm(Pageable pageable);
    CarInspectionInfoResponseDto getQldbCarInfoList(String carNum, String accidentMetaId, String exchangeMetaId);
    int getCarDistance(String carNum);
    void updateSaleFormStatus(Long saleFormId);
}
