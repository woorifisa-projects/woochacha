package com.woochacha.backend.domain.product.service;

import com.woochacha.backend.domain.product.dto.ProdcutResponseDto;
import com.woochacha.backend.domain.product.dto.ProductDetailResponseDto;

import java.util.List;

public interface ProductService {
    List<ProdcutResponseDto> findAllProduct();

    ProductDetailResponseDto findDetailProduct(Long productId);
}
