package com.woochacha.backend.domain.mypage.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EditProductDto {

    private String title; // 제목 (모델 + 차량명 + 년형)
    private Integer price; // 현재 가격
    private String carImage; // 매물 대표 이미지

    public EditProductDto(String title, Integer price, String carImage) {
        this.title = title;
        this.price = price;
        this.carImage = carImage;
    }
}