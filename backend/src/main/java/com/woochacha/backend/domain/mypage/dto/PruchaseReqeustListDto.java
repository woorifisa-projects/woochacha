package com.woochacha.backend.domain.mypage.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PruchaseReqeustListDto {

    private String title; // 제목 : 모델+차량명+연량
    private Integer price; // 가격
    private String branch; // 차고지 지역
    private Integer distance; // 주행거리

    public PruchaseReqeustListDto(String title, Integer price, String branch, Integer distance) {
        this.title = title;
        this.price = price;
        this.branch = branch;
        this.distance = distance;
    }
}
