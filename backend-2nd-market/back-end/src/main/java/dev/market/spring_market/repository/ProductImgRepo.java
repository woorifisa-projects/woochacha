package dev.market.spring_market.repository;

import dev.market.spring_market.entity.Product;
import dev.market.spring_market.entity.ProductImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductImgRepo extends JpaRepository<ProductImg, Long> {
    List<ProductImg> findAll();

    List<ProductImg> findByProductImage(String productImage);

    ProductImg save(ProductImg productImg);
}
