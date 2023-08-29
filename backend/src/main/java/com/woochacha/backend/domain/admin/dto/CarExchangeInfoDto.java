package com.woochacha.backend.domain.admin.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CarExchangeInfoDto {
    private String exchangeType;
    private String exchangeDesc;
    private String exchangeDate;
}
