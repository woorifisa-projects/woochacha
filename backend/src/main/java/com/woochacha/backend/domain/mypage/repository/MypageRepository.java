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
            "ci.imageUrl " +
            "FROM CarImage ci " +
            "JOIN ci.product p " +
            "JOIN p.carDetail cd " +
            "JOIN p.saleForm sf " +
            "WHERE p.status.id = 4 " +
            "AND ci.imageUrl LIKE '%/1' " +
            "AND sf.member.id = :memberId " +
            "ORDER BY p.createdAt ASC ")
    Page<Object[]> getRegisteredProductsByUserId(@Param("memberId") Long memberId, Pageable pageable);

//    // TODO: createAt을 게시글 등록일이 아닌 판매일로 수정 (transaction 테이블) -> 양방향 매핑으로 수정
    // 마이페이지 - 판매 이력 조회 (product.id가 같은 행은 하나만(첫 번째 행) 출력)
    @Query("SELECT " +
            "CONCAT(CAST(cd.model.name AS string), ' ', cd.carName.name, ' ', CAST(cd.year AS string), '년형') AS title, " +
            "cd.distance, " +
            "CAST(sf.branch.name AS string) AS branch, " +
            "p.price, " +
            "ci.imageUrl " +
            "FROM CarImage ci " +
            "JOIN ci.product p " +
            "JOIN p.carDetail cd " +
            "JOIN p.saleForm sf " +
            "WHERE p.status.id = 5 " +
            "AND ci.imageUrl LIKE '%/1' " +
            "AND sf.member.id = :memberId " +
            "ORDER BY p.createdAt ASC ")
    Page<Object[]> getSoldProductsByMemberId(@Param("memberId") Long userId, Pageable pageable);

//    // TODO: 이미지 하나 가져오는 부분 리팩토링
//    // TODO: dummy data 추가로 넣어서 확인
//    // 마이페이지 - 구매 이력 조회
    @Query("SELECT " +
            "CONCAT(CAST(cd.model.name AS string), ' ', cd.carName.name, ' ', CAST(cd.year AS string), '년형') AS title, " +
            "cd.distance, " +
            "CAST(sf.branch.name AS string) AS branch, " +
            "p.price, " +
            "ci.imageUrl " +
            "FROM Transaction tr " +
            "JOIN tr.purchaseForm pf " +
            "JOIN tr.saleForm sf " +
            "JOIN pf.product p " +
            "JOIN p.carDetail cd " +
            "JOIN p.transactionList ci " +
            "WHERE tr.purchaseForm.member.id = :memberId " +
            "AND ci.imageUrl LIKE '%/1' " +
            "ORDER BY tr.createdAt ASC ")
    Page<Object[]> getPurchaseProductsByMemberId(@Param("memberId") Long memberId, Pageable pageable);
}



