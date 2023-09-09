package com.woochacha.backend.domain.admin.dto.approve;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
public class CarExchangeInfoDto {
    // 교체이력 dto
    private String exchangeType;
    private String exchangeDesc;
    private String exchangeDate;

    public CarExchangeInfoDto(String exchangeType, String exchangeDesc, String exchangeDate) {
        this.exchangeType = exchangeType;
        this.exchangeDesc = exchangeDesc;
        this.exchangeDate = exchangeDate;
    }
}
