package com.woochacha.backend.domain.mypage.repository;

import com.woochacha.backend.domain.mypage.dto.EditProductDto;
import com.woochacha.backend.domain.sale.entity.SaleForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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
            "p.id, " +
            "p.status.id " +
            "FROM CarImage ci " +
            "JOIN ci.product p " +
            "JOIN p.carDetail cd " +
            "JOIN p.saleForm sf " +
            "WHERE p.status.id IN (4, 6, 9) " +
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
            "p.id, " +
            "p.status.id " +
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
            "p.id, " +
            "p.status.id " +
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

    // 판매 신청폼 조회
    @Query("SELECT sf.carNum, sf.createdAt, CAST(sf.branch.name AS string), CAST(sf.carStatus.status AS string) " +
            "FROM SaleForm AS sf " +
            "WHERE sf.member.id = :memberId " +
            "ORDER BY sf.createdAt ASC ")
    Page<Object[]> getSaleFormsByMemberId(@Param("memberId") Long memberId, Pageable pageable);

    // 구매 신청폼 조쇠
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

    // 가격 수정폼 데이터 조회
    @Query("SELECT new com.woochacha.backend.domain.mypage.dto.EditProductDto(CONCAT(CAST(p.carDetail.model.name AS string), ' ', CAST(p.carDetail.carName.name AS string), ' ', CAST(p.carDetail.year AS string), '년형'), " +
            "p.price, ci.imageUrl ) " +
            "FROM Product p " +
            "JOIN p.carImages ci " +
            "JOIN p.saleForm sf " +
            "WHERE sf.member.id = :memberId " +
            "AND p.id = :productId " +
            "AND ci.imageUrl LIKE '%/1' ")
    EditProductDto getProductEditRequestInfo(@Param("memberId") Long memberId, @Param("productId") Long productId);

    // 상품 수정 신청시 수정 가격 저장, status 변경
    @Modifying
    @Query("UPDATE Product p SET p.updatePrice = :updatePrice, p.status = 9 WHERE p.id = :productId")
    void updatePrice(@Param("productId") Long productId, @Param("updatePrice") Integer updatePrice);

    // 등록한 매물 삭제 신청 (product 테이블 칼럼 중 status를 6(삭제신청완료)으로 변경
    @Modifying
    @Query("UPDATE Product p SET p.status = 6 WHERE p.id = :productId")
    void requestProductDelete(@Param("productId") Long productId);
}



