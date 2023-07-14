package dev.market.spring_market.dto;

import dev.market.spring_market.entity.ProductImg;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter

public class ProductImgDTO {
    private String productImage;

    public static ProductImgDTO from(ProductImg productImg) {
        final String productImage = productImg.getProductImage();

        return ProductImgDTO.builder().productImage(productImage).build();
    }
}
