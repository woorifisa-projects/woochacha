package com.woochacha.backend.domain.admin.repository;

import com.woochacha.backend.domain.admin.dto.magageProduct.EditProductDto;
import com.woochacha.backend.domain.admin.dto.manageMember.MemberLogDto;
import com.woochacha.backend.domain.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ManageProductFormRepository extends JpaRepository<Product, Long> {

    // 매물 관리 리스트 조회
    @Query("SELECT p.id, CONCAT(CAST(cd.model.name AS string), ' ', cd.carName.name, ' ', CAST(cd.year AS string), '년형') AS title, " +
            "m.name, m.phone, p.status.id " +
            "FROM Product p " +
            "JOIN p.carDetail cd " +
            "JOIN p.saleForm sf " +
            "JOIN sf.member m " +
            "WHERE p.status.id IN (6, 9) " +
            "ORDER BY p.updatedAt ASC ")
    Page<Object[]> getDeleteEditForm(Pageable pageable);

    // 매물 삭제 신청 처리
    @Modifying
    @Query("UPDATE Product p SET p.status = 7 WHERE p.id = :productId")
    void deleteProduct(@Param("productId") Long productId);

    // 매물 가격 수정 처리
    @Query("SELECT new com.woochacha.backend.domain.admin.dto.magageProduct.EditProductDto(CONCAT(CAST(cd.model.name AS string), ' ', cd.carName.name, ' ', CAST(cd.year AS string), '년형'), ci.imageUrl, p.price, p.updatePrice) " +
            "FROM Product p " +
            "JOIN p.carDetail cd " +
            "JOIN p.carImages ci " +
            "WHERE p.id = :productId " +
            "AND ci.imageUrl LIKE '%/1' ")
    EditProductDto getEditForm(@Param("productId") Long productId);

    // 매물 가격 수정 반려
    @Modifying
    @Query("UPDATE Product p SET p.status = 4 WHERE p.id = :productId")
    void denyEditRequest(@Param("productId") Long productId);

    // 매물 가격 수정 승인
    @Modifying
    @Query("UPDATE Product p SET p.status = 4, p.price = p.updatePrice WHERE p.id = :productId")
    void permitEditRequest(@Param("productId") Long productId);

    @Query("SELECT new com.woochacha.backend.domain.admin.dto.manageMember.MemberLogDto(l.email, l.name, l.date, l.type, l.etc) " +
            "FROM Log l, Member m " +
            "WHERE l.email = m.email AND m.id = :memberId " +
            "ORDER BY l.date ASC ")
    Page<MemberLogDto> findAllMemberLog(@Param("memberId") Long memberId, Pageable pageable);
}
