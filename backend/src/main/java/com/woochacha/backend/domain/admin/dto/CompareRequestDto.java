package com.woochacha.backend.domain.admin.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;


@Getter
@Builder
public class CompareRequestDto {
    private int distance;
    private Long saleFormId;
    private List<CarAccidentInfoDto> carAccidentInfoDtoList;
    private List<CarExchangeInfoDto> carExchangeInfoDtoList;
}
