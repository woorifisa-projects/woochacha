package com.woochacha.backend.domain.admin.dto.QLDBtoRDS;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CarAccidentInfoDto {

    private short accidentId;
    private String carNum;
}
