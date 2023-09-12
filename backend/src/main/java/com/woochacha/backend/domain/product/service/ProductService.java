package com.woochacha.backend.domain.product.service;

import com.woochacha.backend.domain.product.dto.ProductAllResponseDto;
import com.woochacha.backend.domain.product.dto.ProductDetailResponseDto;
import com.woochacha.backend.domain.product.dto.ProductPurchaseRequestDto;
import com.woochacha.backend.domain.product.dto.all.ProductInfo;
import com.woochacha.backend.domain.product.dto.filter.ProductFilterInfo;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    ProductAllResponseDto findAllProduct(Pageable pageable);
    ProductDetailResponseDto findDetailProduct(Long productId);
    PageImpl<ProductInfo> findFilteredProduct(ProductFilterInfo productFilterInfo, Pageable pageable);
    void applyPurchaseForm(ProductPurchaseRequestDto productPurchaseRequestDto);

    PageImpl<ProductInfo> findSearchedProduct(String keyword, Pageable pageable);
}
