package com.woochacha.backend.domain.mypage.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
public class ProductResponseDto {
    private String title; // 제목(model + car_name + year)

    private Integer distance; // 주행 거리

    private String branch; // 지점

    private Integer price; // 가격

    private String imageUrl; // 사진

    public ProductResponseDto(String title, Integer distance, String branch, Integer price, String imageUrl) {
        this.title = title;
        this.distance = distance;
        this.branch = branch;
        this.price = price;
        this.imageUrl = imageUrl;
    }

}

