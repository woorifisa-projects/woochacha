package com.woochacha.backend.domain.admin.dto.approve;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
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
