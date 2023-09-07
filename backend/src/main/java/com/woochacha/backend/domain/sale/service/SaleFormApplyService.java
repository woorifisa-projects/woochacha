package com.woochacha.backend.domain.sale.service;

import com.woochacha.backend.domain.sale.dto.BranchDto;

import java.util.List;

public interface SaleFormApplyService {
    List<BranchDto> getBranchList();
    String submitCarSaleForm(String carNum, Long memberId, Long branchId);
    String findCarNum(Long saleFormId);
    void saveSaleForm(String carNum, Long memberId, Long branchId);
}
