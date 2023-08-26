package com.woochacha.backend.domain.product.dto.detail;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class ProductDetailInfo {
    private short capacity; // 승자 정원

    private Integer distance; // 주행 거리

    private String carType; // 차종

    private String fuelName; // 연료

    private String transmissionName; // 변속기

    private List<ProductAccidentInfo> produdctAccidentInfoList; // 사고 종류

    private List<ProductExchangeInfo> productExchangeInfoList; // 교체 부위

}
