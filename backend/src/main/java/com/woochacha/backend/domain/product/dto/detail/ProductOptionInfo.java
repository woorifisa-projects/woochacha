package com.woochacha.backend.domain.product.dto.detail;

import lombok.Data;

@Data
public class ProductOptionInfo {
    private String option;
    private Byte whether;

    public ProductOptionInfo(String option, Byte whether) {
        this.option = option;
        this.whether = whether;
    }
}
