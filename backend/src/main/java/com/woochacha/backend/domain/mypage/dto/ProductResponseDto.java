package com.woochacha.backend.domain.mypage.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProductResponseDto {

    private String carName;
    private String imageUrl;
    private Integer price;
    private Short year;
    private Integer distance;
    private Long branchId;
    private LocalDateTime createdAt;

    public ProductResponseDto(String carName, String imageUrl, Integer price, Short year, Integer distance, Long branchId, LocalDateTime createdAt) {
        this.carName = carName;
        this.imageUrl = imageUrl;
        this.price = price;
        this.year = year;
        this.distance = distance;
        this.branchId = branchId;
        this.createdAt = createdAt;
    }
}
