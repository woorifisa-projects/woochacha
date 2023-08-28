package com.woochacha.backend.domain.admin.dto;

import com.woochacha.backend.domain.status.entity.CarStatusList;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
public class ApproveSaleResponseDto {

    private String name;
    private CarStatusList status;
    private String carNum;

}
