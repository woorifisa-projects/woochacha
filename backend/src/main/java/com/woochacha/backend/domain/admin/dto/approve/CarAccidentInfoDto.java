package com.woochacha.backend.domain.admin.dto.approve;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
public class CarAccidentInfoDto {
    // 사고이력 dto
    private String accidentType;
    private String accidentDesc;
    private String accidentDate;

    public CarAccidentInfoDto(String accidentType, String accidentDesc, String accidentDate) {
        this.accidentType = accidentType;
        this.accidentDesc = accidentDesc;
        this.accidentDate = accidentDate;
    }
}
