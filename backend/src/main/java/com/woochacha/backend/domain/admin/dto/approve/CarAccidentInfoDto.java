package com.woochacha.backend.domain.admin.dto.approve;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
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
