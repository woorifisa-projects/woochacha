package com.woochacha.backend.domain.product.service;

import com.woochacha.backend.domain.product.dto.ProductAllResponseDto;
import com.woochacha.backend.domain.product.dto.ProductDetailResponseDto;
import com.woochacha.backend.domain.product.dto.ProductPurchaseRequestDto;
import com.woochacha.backend.domain.product.dto.all.ProductInfo;
import com.woochacha.backend.domain.product.dto.filter.ProductFilterInfo;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    ProductAllResponseDto findAllProduct(Pageable pageable);
    ProductDetailResponseDto findDetailProduct(Long productId);
    List<ProductInfo> findFilteredProduct(ProductFilterInfo productFilterInfo);
    void applyPurchaseForm(ProductPurchaseRequestDto productPurchaseRequestDto);

    List<ProductInfo> findSearchedProduct(String keyword);
}
