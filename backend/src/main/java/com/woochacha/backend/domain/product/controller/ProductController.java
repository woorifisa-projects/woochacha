package com.woochacha.backend.domain.product.controller;

import com.querydsl.core.Tuple;
import com.woochacha.backend.domain.product.dto.ProdcutAllResponseDto;
import com.woochacha.backend.domain.product.dto.ProductDetailResponseDto;
import com.woochacha.backend.domain.product.dto.ProductFilterResponseDto;
import com.woochacha.backend.domain.product.dto.all.ProductFilterInfo;
import com.woochacha.backend.domain.product.dto.all.ProductInfo;
import com.woochacha.backend.domain.product.service.ProductService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ProdcutAllResponseDto findAllProduct() {
        return productService.findAllProduct();
    }

    @GetMapping("/{productId}")
    public ProductDetailResponseDto findDetailProduct(@PathVariable Long productId) {
        return productService.findDetailProduct(productId);
    }

    @PostMapping("/filter")
    public List<ProductFilterResponseDto> findFilteredProduct(@RequestBody ProductFilterInfo productFilterInfo) {
        return productService.findFilteredProduct(productFilterInfo);
    }
}
