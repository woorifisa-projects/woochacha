package com.woochacha.backend.domain.admin.dto.QLDBtoRDS;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDto {

    private Long saleId;
    private Integer price;
    private short status;
    private String carNum;
}
