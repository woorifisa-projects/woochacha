package com.woochacha.backend.domain.product.service;

import com.woochacha.backend.domain.product.dto.*;
import com.woochacha.backend.domain.product.dto.filter.ProductFilterInfo;
import com.woochacha.backend.domain.product.dto.all.ProductInfo;

import java.util.List;

public interface ProductService {
    ProductAllResponseDto findAllProduct();
    ProductDetailResponseDto findDetailProduct(Long productId);
    List<ProductInfo> findFilteredProduct(ProductFilterInfo productFilterInfo);

    List<ProductInfo> findSearchedProduct(String keyword);
}
