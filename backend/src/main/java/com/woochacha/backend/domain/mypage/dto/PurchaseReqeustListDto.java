package com.woochacha.backend.domain.mypage.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PurchaseReqeustListDto {

    private String title; // 제목 : 모델+차량명+연량
    private Integer price; // 가격
    private String branch; // 차고지 지역
    private Integer distance; // 주행거리
    private Long productId; // 게시글 id (상세페이지로 이동할떄 사용)

    public PurchaseReqeustListDto(String title, Integer price, String branch, Integer distance, Long productId) {
        this.title = title;
        this.price = price;
        this.branch = branch;
        this.distance = distance;
        this.productId = productId;
    }
}
