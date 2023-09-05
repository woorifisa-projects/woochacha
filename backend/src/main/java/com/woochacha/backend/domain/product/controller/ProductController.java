package com.woochacha.backend.domain.product.controller;

import com.woochacha.backend.domain.log.service.LogService;
import com.woochacha.backend.domain.product.dto.ProductAllResponseDto;
import com.woochacha.backend.domain.product.dto.ProductDetailResponseDto;
import com.woochacha.backend.domain.product.dto.ProductPurchaseRequestDto;
import com.woochacha.backend.domain.product.dto.all.ProductInfo;
import com.woochacha.backend.domain.product.dto.filter.ProductFilterInfo;
import com.woochacha.backend.domain.product.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    private final LogService logService;

    public ProductController(ProductService productService, LogService logService) {
        this.productService = productService;
        this.logService = logService;
    }

    @GetMapping
    public ProductAllResponseDto findAllProduct() {
        return productService.findAllProduct();
    }

    @GetMapping("/{productId}")
    public ProductDetailResponseDto findDetailProduct(@PathVariable Long productId) {
        return productService.findDetailProduct(productId);
    }

    @PostMapping("/filter")
    public List<ProductInfo> findFilteredProduct(@RequestBody ProductFilterInfo productFilterInfo) {
        return productService.findFilteredProduct(productFilterInfo);
    }

    @PostMapping("/purchase")
    public ResponseEntity<Boolean> applyProductPurchase(@RequestBody ProductPurchaseRequestDto productPurchaseRequestDto) {
        productService.applyPurchaseForm(productPurchaseRequestDto);
        logService.savedMemberLogWithTypeAndEtc(productPurchaseRequestDto.getMemberId(), "구매 신청", "/product/" + productPurchaseRequestDto.getProductId());
        return ResponseEntity.ok(true);
    }

    @GetMapping("/search")
    public List<ProductInfo> findSearchedProduct(@RequestParam(value="keyword") String keyword) {
        return productService.findSearchedProduct(keyword);
    }
}
