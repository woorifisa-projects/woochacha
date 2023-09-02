package com.woochacha.backend.domain.admin.dto.approve;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CarInspectionInfoResponseDto {
    // qldb에서 가지고 오는 모든 사고의 data를 저장하기 위한 dto
    private String carNum;
    private String carOwnerName;
    private String carOwnerPhone;
    private int carDistance;
    private List<CarAccidentInfoDto> carAccidentInfoDtoList;
    private List<CarExchangeInfoDto> carExchangeInfoDtoList;
}