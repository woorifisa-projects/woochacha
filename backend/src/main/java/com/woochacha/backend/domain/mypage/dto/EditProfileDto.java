package com.woochacha.backend.domain.mypage.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class EditProfileDto {
    private String imageUrl; // 프로필 사진
    private String name; // 사용자 이름
}