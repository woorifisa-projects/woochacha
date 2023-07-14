package dev.market.spring_market.dto;

import dev.market.spring_market.entity.ProductImg;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class ProductImgRequest {
	@NotNull
    private String productImage;

    @Builder
	public ProductImgRequest(@NotNull String productImage) {
		super();
		this.productImage = productImage;
	}
}
