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
    @Query("SELECT " +
            "CONCAT(CAST(cd.model.name AS string), ' ', cd.carName.name, ' ', CAST(cd.year AS string), '년형') AS title, " +
            "cd.distance, " +
            "CAST(sf.branch.name AS string) AS branch, " +
            "p.price, " +
            "ci.imageUrl, " +
            "p.id " +
            "FROM CarImage ci " +
            "JOIN ci.product p " +
            "JOIN p.carDetail cd " +
            "JOIN p.saleForm sf " +
            "WHERE p.status.id = 4 " +
            "AND ci.imageUrl LIKE '%/1' " +
            "AND sf.member.id = :memberId " +
            "ORDER BY p.createdAt ASC ")
    Page<Object[]> getRegisteredProductsByMemberId(@Param("memberId") Long memberId, Pageable pageable);

//  마이페이지 - 판매 이력 조회
    @Query("SELECT " +
            "CONCAT(CAST(cd.model.name AS string), ' ', cd.carName.name, ' ', CAST(cd.year AS string), '년형') AS title, " +
            "cd.distance, " +
            "CAST(sf.branch.name AS string) AS branch, " +
            "p.price, " +
            "ci.imageUrl, " +
            "p.id " +
            "FROM CarImage ci " +
            "JOIN ci.product p " +
            "JOIN p.carDetail cd " +
            "JOIN p.saleForm sf " +
            "WHERE p.status.id = 5 " +
            "AND ci.imageUrl LIKE '%/1' " +
            "AND sf.member.id = :memberId " +
            "ORDER BY p.createdAt ASC ")
    Page<Object[]> getSoldProductsByMemberId(@Param("memberId") Long memberId, Pageable pageable);

    // 마이페이지 - 구매 이력 조회
    @Query("SELECT " +
            "CONCAT(CAST(cd.model.name AS string), ' ', cd.carName.name, ' ', CAST(cd.year AS string), '년형') AS title, " +
            "cd.distance, " +
            "CAST(sf.branch.name AS string) AS branch, " +
            "p.price, " +
            "ci.imageUrl, " +
            "p.id " +
            "FROM Transaction tr " +
            "JOIN tr.purchaseForm pf " +
            "JOIN tr.saleForm sf " +
            "JOIN pf.product p " +
            "JOIN p.carDetail cd " +
            "JOIN p.carImages ci " +
            "WHERE tr.purchaseForm.member.id = :memberId " +
            "AND ci.imageUrl LIKE '%/1' " +
            "ORDER BY tr.createdAt ASC ")
    Page<Object[]> getPurchaseProductsByMemberId(@Param("memberId") Long memberId, Pageable pageable);

    @Query("SELECT " +
            "CONCAT(CAST(cd.model.name AS string), ' ', cd.carName.name, ' ', CAST(cd.year AS string), '년형') AS title, " +
            "p.price, " +
            "CAST(sf.branch.name AS string) AS branch, " +
            "cd.distance, " +
            "p.id, " +
            "pf.status " +
            "FROM PurchaseForm pf " +
            "JOIN pf.product p " +
            "JOIN p.saleForm sf " +
            "JOIN p.carDetail cd " +
            "JOIN p.carImages ci " +
            "WHERE pf.member.id = :memberId " +
            "AND ci.imageUrl LIKE '%/1' " +
            "ORDER BY pf.createdAt ASC ")
    Page<Object[]> getPurchaseRequestByMemberId(@Param("memberId") Long memberId, Pageable pageable);
}



