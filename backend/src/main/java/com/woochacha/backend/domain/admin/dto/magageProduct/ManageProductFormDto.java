package com.woochacha.backend.domain.admin.dto.magageProduct;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 매물 관리 리스트 페이지에서 수정/신청 폼을 조회할 때 데이터를 전달하는 Dto
@Builder
@Getter
@NoArgsConstructor
public class ManageProductFormDto {

    private Long productId; // 게시글 url을 위한 productId

    private String title; // 게시글 url의 텍스트로 보여줄 게시글 title

    private String sellerName; // 판매자명

    private String sellerEmail; // 판매자 이메일

    private String manageType; // 신청폼의 종류 (삭제 / 수정)

    public ManageProductFormDto(Long productId, String title, String sellerName, String sellerEmail, String manageType) {
        this.productId = productId;
        this.title = title;
        this.sellerName = sellerName;
        this.sellerEmail = sellerEmail;
        this.manageType = manageType;
    }
}
