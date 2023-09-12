package com.woochacha.backend.domain.product.controller;

import com.woochacha.backend.domain.product.dto.ProductAllResponseDto;
import com.woochacha.backend.domain.product.dto.ProductDetailResponseDto;
import com.woochacha.backend.domain.product.dto.ProductPurchaseRequestDto;
import com.woochacha.backend.domain.product.dto.all.ProductInfo;
import com.woochacha.backend.domain.product.dto.filter.ProductFilterInfo;
import com.woochacha.backend.domain.product.service.ProductService;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ProductAllResponseDto findAllProduct(Pageable pageable) {
        return productService.findAllProduct(pageable);
    }

    @GetMapping("/{productId}")
    public ProductDetailResponseDto findDetailProduct(@PathVariable Long productId) {
        return productService.findDetailProduct(productId);
    }

    @PostMapping("/filter")
    public PageImpl<ProductInfo> findFilteredProduct(@RequestBody ProductFilterInfo productFilterInfo, Pageable pageable) {
        return productService.findFilteredProduct(productFilterInfo, pageable);
    }

    @PostMapping("/purchase")
    public ResponseEntity<Boolean> applyProductPurchase(@RequestBody ProductPurchaseRequestDto productPurchaseRequestDto) {
        productService.applyPurchaseForm(productPurchaseRequestDto);
        return ResponseEntity.ok(true);
    }

    @GetMapping("/search")
    public PageImpl<ProductInfo> findSearchedProduct(@RequestParam(value="keyword") String keyword, Pageable pageable) {
        return productService.findSearchedProduct(keyword, pageable);
    }
}
