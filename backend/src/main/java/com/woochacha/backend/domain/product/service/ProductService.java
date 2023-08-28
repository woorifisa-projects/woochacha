package com.woochacha.backend.domain.product.service;

import com.woochacha.backend.domain.product.dto.ProdcutAllResponseDto;
import com.woochacha.backend.domain.product.dto.ProductDetailResponseDto;

import java.util.List;

public interface ProductService {
    ProdcutAllResponseDto findAllProduct();

    ProductDetailResponseDto findDetailProduct(Long productId);
}
