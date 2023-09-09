package com.woochacha.backend.domain.admin.dto.approve;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CarAccidentInfoDto {
    // 사고이력 dto
    private String accidentType;
    private String accidentDesc;
    private String accidentDate;


}
