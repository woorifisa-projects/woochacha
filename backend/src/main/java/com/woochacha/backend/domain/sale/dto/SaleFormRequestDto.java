package com.woochacha.backend.domain.sale.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaleFormRequestDto {
    private String carNum;
    private Long memberId;
    private Long branchId;
}
