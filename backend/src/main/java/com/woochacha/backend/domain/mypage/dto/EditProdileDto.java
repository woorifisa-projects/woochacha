package com.woochacha.backend.domain.mypage.dto;

import lombok.Builder;
import lombok.Data;

// 프로필 수정시 GET요청에서 사용할 Dto
@Data
@Builder
public class EditProdileDto {
    private String imageUrl; // 프로필 사진
    private String name; // 사용자 이름
}
