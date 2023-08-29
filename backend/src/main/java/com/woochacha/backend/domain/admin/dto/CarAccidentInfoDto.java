package com.woochacha.backend.domain.admin.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CarAccidentInfoDto {
    private String accidentType;
    private String accidentDesc;
    private String accidentDate;


}
