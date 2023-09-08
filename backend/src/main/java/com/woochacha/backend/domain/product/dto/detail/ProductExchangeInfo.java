package com.woochacha.backend.domain.product.dto.detail;

import lombok.*;

@Getter
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
public class ProductExchangeInfo {
    private String type;
    private int count;
}
