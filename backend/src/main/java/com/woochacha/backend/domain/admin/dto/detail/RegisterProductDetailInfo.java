package com.woochacha.backend.domain.admin.dto.detail;

import com.woochacha.backend.domain.product.dto.detail.ProductAccidentInfo;
import com.woochacha.backend.domain.product.dto.detail.ProductExchangeInfo;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class RegisterProductDetailInfo {

    private String model; // 브랜드명

    private String color; // 색상

    private short year; // 연식

    private short capacity; // 승자 정원

    private Integer distance; // 주행 거리

    private String carType; // 차종

    private String fuelName; // 연료

    private String transmissionName; // 변속기

    private List<RegisterProductAccidentInfo> produdctAccidentInfoList; // 사고 종류

    private List<RegisterProductExchangeInfo> productExchangeInfoList; // 교체 부위

}
