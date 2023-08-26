package com.woochacha.backend.domain.product.dto.detail;

import lombok.Data;

@Data
public class ProductOwnerInfo {
    private String sellerName; // 판매자 이름

    private String sellerEmail; // 판매자 이메일

    private String sellerProfileImage; // 판매자 프로필 사진
}
