package com.woochacha.backend.domain.product.repository;

import com.woochacha.backend.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // 상품 수정 신청시 수정 가격 저장, status 변경
    @Modifying
    @Query("UPDATE Product p SET p.updatePrice = :updatePrice, p.status.id = 9 WHERE p.id = :productId")
    void updatePrice(@Param("productId") Long productId, @Param("updatePrice") Integer updatePrice);
}
