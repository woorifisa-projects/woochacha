package com.woochacha.backend.domain.product.service;

import com.querydsl.core.Tuple;
import com.woochacha.backend.domain.product.dto.*;
import com.woochacha.backend.domain.product.dto.all.ProductFilterInfo;
import com.woochacha.backend.domain.product.dto.all.ProductInfo;

import java.util.List;

public interface ProductService {
    ProdcutAllResponseDto findAllProduct();
    ProductDetailResponseDto findDetailProduct(Long productId);
    List<ProductFilterResponseDto> findFilteredProduct(ProductFilterInfo productFilterInfo);
}
