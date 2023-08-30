package com.woochacha.backend.domain.sale.service;

import com.woochacha.backend.domain.sale.entity.Branch;
import java.util.List;

public interface SaleFormApplyService {
    List<Branch> getBranchList();
    Boolean submitCarSaleForm(String carNum, Long memberId);
    String findCarNum(Long saleFormId);
    void saveSaleForm(String carNum, Long memberId, Long branchId);
}
