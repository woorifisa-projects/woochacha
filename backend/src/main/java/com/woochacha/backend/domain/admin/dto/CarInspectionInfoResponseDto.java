package com.woochacha.backend.domain.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CarInspectionInfoResponseDto {
    private String carNum;
    private String carOwnerName;
    private String carOwnerPhone;
    private int carDistance;
    List<CarAccidentInfoDto> carAccidentInfoDtoList;
    List<CarExchangeInfoDto> carExchangeInfoDtoList;
}
