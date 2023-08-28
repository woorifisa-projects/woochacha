package com.woochacha.backend.domain.product.dto;

import com.woochacha.backend.domain.product.dto.all.ProductFilterInfo;
import com.woochacha.backend.domain.product.dto.all.ProductInfo;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ProdcutAllResponseDto {
    private List<ProductInfo> productInfo; // 매물 전체 정보
    private ProductFilterInfo productFilterInfo; // 필터링 목록 정보
}
