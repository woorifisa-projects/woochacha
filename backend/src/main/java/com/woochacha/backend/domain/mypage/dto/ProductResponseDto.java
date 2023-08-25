package com.woochacha.backend.domain.mypage.dto;

import com.woochacha.backend.domain.sale.entity.Branch;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ProductResponseDto {

    private String carName; // 차량명
    private String imageUrl; // 차량 첫 번째 사진
    private Integer price; // 차량 가격
    private Short year; // 연식
    private Integer distance; // 주행거리
    private Branch branch; // 차고지 지역
    private LocalDateTime createdAt;

    public ProductResponseDto(String carName, String imageUrl, Integer price, Short year, Integer distance, Branch branch, LocalDateTime createdAt) {
        this.carName = carName;
        this.imageUrl = imageUrl;
        this.price = price;
        this.year = year;
        this.distance = distance;
        this.branch = branch;
        this.createdAt = createdAt;
    }
}
