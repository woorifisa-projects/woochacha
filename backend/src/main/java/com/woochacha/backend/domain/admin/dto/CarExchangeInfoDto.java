package com.woochacha.backend.domain.admin.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CarExchangeInfoDto {
    // 교체이력 dto
    private String exchangeType;
    private String exchangeDesc;
    private String exchangeDate;
}
