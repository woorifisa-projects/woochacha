package com.woochacha.backend.domain.admin.dto;

import lombok.*;

@Getter
@AllArgsConstructor
@Builder
public class CarInspectionInfoDto {
    private String distance;
    private String accidentType;
    private String accidentDesc;
    private String accidentDate;
    private String exchangeType;
    private String exchangeDesc;
    private String exchangeDate;
}
