package com.woochacha.backend.domain.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class CarInspectionRequestDto {
    private String carNum;
    private int distance;
}
