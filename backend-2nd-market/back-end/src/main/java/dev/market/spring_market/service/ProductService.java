package dev.market.spring_market.service;

import java.util.List;

import dev.market.spring_market.dto.ProductRequest;
import dev.market.spring_market.dto.ProductResponse;

public interface ProductService {
    List<ProductResponse> findAll();

    ProductResponse findById(Long productId);
    
    void save(ProductRequest productRequest);

    void update(Long id, ProductRequest productRequest);

    void delete(Long id);
}
