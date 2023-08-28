package com.woochacha.backend.domain.product.dto;

import lombok.Data;

@Data
public class ProductFilterResponseDto {
    private int id;

    private String title; // 제목(model + car_name + year)

    private Integer distance; // 주행 거리

    private String branch; // 지점

    private Integer price; // 가격

    private String imageUrl; // 사진

    private int typeId;
    private int modelId;

}
