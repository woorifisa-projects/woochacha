package com.woochacha.backend.domain.product.controller;

import com.woochacha.backend.domain.product.dto.ProductAllResponseDto;
import com.woochacha.backend.domain.product.dto.ProductDetailResponseDto;
import com.woochacha.backend.domain.product.dto.filter.ProductFilterInfo;
import com.woochacha.backend.domain.product.dto.all.ProductInfo;
import com.woochacha.backend.domain.product.service.ProductService;
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

    @GetMapping("/search")
    public List<ProductInfo> findSearchedProduct(@RequestParam(value="keyword") String keyword) {
        return productService.findSearchedProduct(keyword);
    }
}
