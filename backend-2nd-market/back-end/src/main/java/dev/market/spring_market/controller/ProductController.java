package dev.market.spring_market.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import dev.market.spring_market.dto.ProductIdDTO;
import org.springframework.web.bind.annotation.*;

import dev.market.spring_market.dto.ProductRequest;
import dev.market.spring_market.dto.ProductResponse;
import dev.market.spring_market.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private Object ResponseEntity;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductResponse> findAll() {
        return productService.findAll();
    }

    @GetMapping(value = "/find-one")
    public ProductResponse findById(@RequestHeader("productId") Long id) {
        return productService.findById(id);
    }

    @PostMapping
    public void saveProduct(@Valid @RequestBody ProductRequest productRequest) {
    	productService.save(productRequest);
    }

    @PatchMapping("/update")
    public void updateProduct(@Valid @RequestBody ProductRequest productRequest) {
        productService.update(productRequest.getProductId(), productRequest);
    }

    @PatchMapping("/delete")
    public void deleteProduct(@Valid @RequestBody ProductIdDTO productIdDTO) {
        productService.delete(productIdDTO.getProductId());
    }
}
