package com.woochacha.backend.domain.admin.dto.approve;

import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class CompareRequestDto {
    // 차량 검사 후 비교하여 교체된 이력에 대해 저장하는 dto
    private int distance;
    private CarAccidentInfoDto carAccidentInfoDto;
    private CarExchangeInfoDto carExchangeInfoDto;
}