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

    // 마이페이지 - 등록한 매물 조회 (product.id가 같은 행은 하나만(첫 번째 행) 출력)
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

    // TODO: createAt을 게시글 등록일이 아닌 판매일로 수정 (transaction 테이블) -> 양방향 매핑으로 수정
    // 마이페이지 - 판매 이력 조회 (product.id가 같은 행은 하나만(첫 번째 행) 출력)
    @Query("SELECT cd.carName, ci.imageUrl, p.price, cd.year, cd.distance, sf.branch, p.createdAt "  +
            "FROM CarImage ci " +
            "JOIN ci.product p " +
            "JOIN p.carDetail cd " +
            "JOIN p.saleForm sf " +
            "JOIN sf.member m "  +
            "WHERE m.id = :userId AND p.status.id = 5 " +
            "AND NOT EXISTS(" +
            "SELECT 1 FROM Product p2 " +
            "WHERE p2.id = p.id AND p2.createdAt < p.createdAt) " +
            "GROUP BY cd.carName, cd.year, cd.distance, sf.branch.id, p.createdAt, p.id")
    Page<Object[]> getSoldProductsByMemberId(@Param("userId") Long userId, Pageable pageable);

    // TODO: dummy data 추가로 넣어서 확인
    // 마이페이지 - 구매 이력 조회
    @Query("SELECT cd.carName, ci.imageUrl, p.price, cd.year, cd.distance, sf.branch, tr.createdAt " +
            "FROM Transaction tr " +
            "JOIN tr.purchaseForm pf " +
            "JOIN tr.saleForm sf " +
            "JOIN pf.product p " +
            "JOIN p.carDetail cd " +
            "JOIN p.transactionList ci " +
            "WHERE tr.purchaseForm.member.id = :userId " +
            "AND NOT EXISTS (" +
            "SELECT 1 FROM Product p2 " +
            "WHERE p2.id = p.id AND p2.createdAt < p.createdAt) " +
            "GROUP BY p.id ")
    Page<Object[]> getPurchaseProductsByMemberId(@Param("userId") Long userId, Pageable pageable);
}



