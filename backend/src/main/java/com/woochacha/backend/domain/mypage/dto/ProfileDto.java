package com.woochacha.backend.domain.mypage.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class ProfileDto {

    private String profileImage; // 프로필 사진
    private String email; // 사용자 이메일
    private String name; // 사용자 이름
    private String phone; // 사용자 전화번호

    public ProfileDto() {}

    public ProfileDto(String profileImage, String email, String name, String phone) {
        this.profileImage = profileImage;
        this.email = email;
        this.name = name;
        this.phone = phone;
    }
}
