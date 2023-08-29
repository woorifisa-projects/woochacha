package com.woochacha.backend.domain.admin.dto;

import com.woochacha.backend.domain.status.entity.CarStatusList;
import lombok.*;

@Getter
@Builder
public class ApproveSaleResponseDto {

    private String name;
    private CarStatusList status;
    private String carNum;

    public ApproveSaleResponseDto(String name, CarStatusList status, String carNum) {
        this.name = name;
        this.status = status;
        this.carNum = carNum;
    }
}
