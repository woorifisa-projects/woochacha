package com.woochacha.backend.domain.mypage.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EditProductDto {

    private String title; // 제목 (모델 + 차량명 + 년형)
    private String carImage; // 매물 대표 이미지
    private Integer price; // 현재 가격

    public EditProductDto(String title, String carImage, Integer price) {
        this.title = title;
        this.carImage = carImage;
        this.price = price;
    }
}
