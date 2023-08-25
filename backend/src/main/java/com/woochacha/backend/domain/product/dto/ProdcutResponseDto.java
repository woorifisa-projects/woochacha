package com.woochacha.backend.domain.product.dto;

import com.woochacha.backend.domain.sale.entity.Branch;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProdcutResponseDto {
    private String title; // 제목(model + car_name + year)

    private Integer distance; // 주행 거리

    private String branch; // 지점

    private Integer price; // 가격

    private String imageUrl; // 사진
}
