package com.woochacha.backend.domain.product.repository;

import com.woochacha.backend.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Modifying
    @Query("UPDATE Product AS p SET p.status = 5 WHERE p.id = :productId")
    void updateProductSuccessStatus(@Param("productId") Long productId);

    @Query("SELECT COUNT(*) FROM Product p " +
            "JOIN p.saleForm sf " +
            "WHERE p.saleForm.member.id = :memberId AND p.status.id = :carStatusId")
    int countSale(@Param("memberId") Long memberId, @Param("carStatusId") short carStatusId);

    @Query("SELECT COUNT(*) FROM Product p " +
            "JOIN p.carDetail cd " +
            "WHERE p.carDetail.carNum = :carNum AND p.status.id = :status")
    int checkCarNum(@Param("carNum") String carNum, @Param("status") short status);
}

