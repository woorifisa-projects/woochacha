package com.woochacha.backend.domain.product.dto;

import com.woochacha.backend.domain.sale.entity.Branch;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProdcutResponseDto {
    private String imageUrl; // 사진

    private String carName; // 제목

    private Short year; // 연식

    private Integer distance; // 주행 거리

    private String branch; // 지점

    private Integer price; // 가격
}
