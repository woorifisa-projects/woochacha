package com.woochacha.backend.domain.admin.service;

import com.woochacha.backend.domain.admin.dto.approve.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ApproveSaleService {
    Page<ApproveSaleResponseDto> getApproveSaleForm(Pageable pageable);
    Boolean updateSaleFormDenyStatus(Long saleFormId);
    CarInspectionInfoResponseDto getQldbCarInfoList(Long saleFormId);
    Boolean compareCarHistory(CompareRequestDto compareRequestDto, Long saleFormId);
}
