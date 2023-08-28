package com.woochacha.backend.domain.mypage.dto;

import lombok.Data;

@Data
public class ProductEditRequestDto {

    private String title;
    private String carImage;
    private Integer price;

    public ProductEditRequestDto(String title, String carImage, Integer price) {
        this.title = title;
        this.carImage = carImage;
        this.price = price;
    }
}
