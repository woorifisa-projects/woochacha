package dev.market.spring_market.dto;

import javax.validation.constraints.NotNull;

import dev.market.spring_market.entity.ProductImg;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductImgResponse {
	@NotNull
    private String productImage;

    public static ProductImgResponse from(ProductImg productImg) {
        final String productImage = productImg.getProductImage();

        return ProductImgResponse.builder().productImage(productImage).build();
    }

    @Builder
	public ProductImgResponse(@NotNull String productImage) {
		super();
		this.productImage = productImage;
	}
}
