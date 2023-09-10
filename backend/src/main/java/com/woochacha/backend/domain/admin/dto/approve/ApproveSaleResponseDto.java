package com.woochacha.backend.domain.admin.dto.approve;

import com.woochacha.backend.domain.status.entity.CarStatusList;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
public class ApproveSaleResponseDto {
    //차량 승인을 위한 dto
    private Long id;
    private String name;
    private String carNum;
    private CarStatusList status;

    public ApproveSaleResponseDto(Long id, String name, String carNum, CarStatusList status) {
        this.id = id;
        this.name = name;
        this.carNum = carNum;
        this.status = status;
    }
}
