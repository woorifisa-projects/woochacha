package com.woochacha.backend.domain.admin.dto.QLDBtoRDS;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CarDetailDto {

    private String carNum;
    private String owner;
    private String phone;
    private Integer distance;
    private short year;
    private short capacity;
    private Integer type;
    private Integer model;
    private Integer fuel;
    private Integer color;
    private Integer transmission;
    private Long carName;
}
