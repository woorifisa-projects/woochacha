package com.woochacha.backend.domain.product.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductPurchaseRequestDto {
    private Long memberId;
    private Long productId;

}
