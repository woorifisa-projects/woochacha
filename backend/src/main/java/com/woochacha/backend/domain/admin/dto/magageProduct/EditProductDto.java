package com.woochacha.backend.domain.admin.dto.magageProduct;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 관리자 페이지 매물 관리에서 특정 매물의 수정처리를 할 떄 팝업창에 띄워줄 데이터를
@Builder
@Getter
@NoArgsConstructor
public class EditProductDto {

    private String title; // 매물 제목

    private String imageUrl; // 매물 사진 (대표 사진 하나)

    private Integer price; // 매물 현재 가격

    private Integer updatePrice; // 매물 수정할 가격

    public EditProductDto(String title, String imageUrl, Integer price, Integer updatePrice) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.price = price;
        this.updatePrice = updatePrice;
    }
}
