package dev.market.spring_market.dto;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductRequest extends ProductIdDTO {
	@NotNull
	private Long userId;
	
	@NotNull @Size(min = 1, max = 45)
    private String title;

	@NotNull
	private int price;

	@Size(min = 0, max = 255)
    private String contents;


	private List<ProductImgRequest> productImgRequests;
    
    @NotNull
    private Long categoryId;

    @Builder
	public ProductRequest(@NotNull Long userId, @NotNull @Size(min = 1, max = 45) String title, @NotNull int price,
						  @Size(min = 0, max = 255) String contents, List<ProductImgRequest> productImgRequest, @NotNull Long categoryId) {
		super();
		this.userId = userId;
		this.title = title;
		this.price = price;
		this.contents = contents;
		this.productImgRequests = productImgRequest;
		this.categoryId = categoryId;
	}
    
}
