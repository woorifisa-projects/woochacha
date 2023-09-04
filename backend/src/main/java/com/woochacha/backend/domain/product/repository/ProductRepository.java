package com.woochacha.backend.domain.product.repository;

import com.querydsl.core.BooleanBuilder;
import com.woochacha.backend.domain.product.dto.all.ProductInfo;
import com.woochacha.backend.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Modifying
    @Query("UPDATE Product AS p SET p.status = 5 WHERE p.id = :productId")
    void updateProductSuccessStatus(@Param("productId") Long productId);
}
