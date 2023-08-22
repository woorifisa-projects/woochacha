package com.woochacha.backend.domain.mypage.repository;

import com.woochacha.backend.domain.sale.entity.SaleForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface MypageRepository extends JpaRepository<SaleForm, Long> {

    // 마이페이지 - 등록한 매물 조회
    @Query("SELECT cd.carName, ci.imageUrl, p.price, cd.year, cd.distance, sf.branch, p.createdAt " +
            "FROM CarImage ci " +
            "JOIN ci.product p " +
            "JOIN p.carDetail cd " +
            "JOIN p.saleForm sf " +
            "JOIN sf.member m " +
            "WHERE m.id = :userId AND p.status.id = 4 " +
            "AND NOT EXISTS (" +
            "SELECT 1 FROM Product p2 " +
            "WHERE p2.id = p.id AND p2.createdAt < p.createdAt)" +
            "GROUP BY cd.carName, cd.year, cd.distance, sf.branch.id, p.createdAt, p.id")
    Page<Object[]> getRegisteredProductsByUserId(@Param("userId") Long userId, Pageable pageable);
}



