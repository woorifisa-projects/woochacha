package com.woochacha.backend.domain.admin.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CarAccidentInfoDto {
    // 사고이력 dto
    private String accidentType;
    private String accidentDesc;
    private String accidentDate;


}
