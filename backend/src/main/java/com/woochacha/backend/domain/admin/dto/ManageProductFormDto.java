package com.woochacha.backend.domain.admin.dto;


import lombok.Data;

// 매물 관리 리스트 페이지에서 수정/신청 폼을 조회할 때 데이터를 전달하는 Dto
@Data
public class ManageProductFormDto {

    private Long productId; // 게시글 url을 위한 productId

    private String sellerName; // 판매자명

    private String formStatus; // 신청폼 상태를 저장하는 필드 (검토전 / 검토완료)

    private String manageType; // 신청폼의 종류 (삭제 / 수정)
}
