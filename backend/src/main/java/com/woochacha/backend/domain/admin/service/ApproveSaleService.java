package com.woochacha.backend.domain.admin.service;

import com.querydsl.core.QueryResults;
import com.woochacha.backend.domain.admin.dto.ApproveSaleResponseDto;
import com.woochacha.backend.domain.admin.dto.CarInspectionInfoDto;
import com.woochacha.backend.domain.sale.entity.SaleForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ApproveSaleService {
    QueryResults<ApproveSaleResponseDto> getApproveSaleForm(Pageable pageable);
}
