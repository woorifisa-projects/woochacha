package com.woochacha.backend.domain.sale.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SaleFormRequestDto {
    private String carNum;
    private Long memberId;
    private Long branchId;
}
