package com.woochacha.backend.domain.admin.dto.approve;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CarExchangeInfoDto {
    // 교체이력 dto
    private String exchangeType;
    private String exchangeDesc;
    private String exchangeDate;
}
