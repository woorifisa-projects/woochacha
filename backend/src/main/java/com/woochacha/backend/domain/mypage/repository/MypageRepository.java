package com.woochacha.backend.domain.mypage.repository;

import com.woochacha.backend.domain.sale.entity.SaleForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface MypageRepository extends JpaRepository<SaleForm, Long> {

    // 마이페이지 - 등록한 매물 조회
    @Query("SELECT cd.carName, ci.imageUrl, p.price, cd.year, cd.distance, sf.branch.id, p.createdAt " +
            "FROM CarImage ci " +
            "JOIN ci.product p " +
            "JOIN p.carDetail cd " +
            "JOIN p.saleForm sf " +
            "JOIN sf.member m " +
            "WHERE m.id= :userId")
    Page<Object[]> getRegisteredProductsByUserId(Long userId, Pageable pageable);
}



