package com.woochacha.backend.domain.admin.service;

import com.querydsl.core.QueryResults;
import com.woochacha.backend.domain.admin.dto.ApproveSaleResponseDto;
import com.woochacha.backend.domain.admin.dto.CarInspectionInfoResponseDto;
import org.springframework.data.domain.Pageable;

public interface ApproveSaleService {
    QueryResults<ApproveSaleResponseDto> getApproveSaleForm(Pageable pageable);
    CarInspectionInfoResponseDto getQldbCarInfoList(String carNum, String accidentMetaId, String exchangeMetaId);
}
