package com.woochacha.backend.domain.product.controller;

import com.woochacha.backend.domain.product.dto.ProdcutResponseDto;
import com.woochacha.backend.domain.product.dto.ProductDetailResponseDto;
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
    public List<ProdcutResponseDto> findAllProduct() {
        return productService.findAllProduct();
    }

    @GetMapping("/{productId}")
    public ProductDetailResponseDto findDetailProduct(@PathVariable Long productId) {
        return productService.findDetailProduct(productId);
    }
}
