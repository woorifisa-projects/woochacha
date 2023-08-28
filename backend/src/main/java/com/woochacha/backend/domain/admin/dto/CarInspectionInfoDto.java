package com.woochacha.backend.domain.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
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
