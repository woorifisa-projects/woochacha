package com.woochacha.backend.domain.product.dto.detail;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.context.annotation.Bean;

import java.util.List;

@Data
public class ProductBasicInfo {
    private String title; // 제목 : 모델+차명+연식

    private String carNum; // 차량 번호

    private String branch; // 지점

    private Integer price; // 가격
}
