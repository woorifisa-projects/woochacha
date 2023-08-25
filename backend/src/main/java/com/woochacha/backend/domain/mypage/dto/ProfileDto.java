package com.woochacha.backend.domain.mypage.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProfileDto {

    private String profileImage;
    private String email;
    private String name;
    private String phone;

    public ProfileDto(String profileImage, String email, String name, String phone) {
        this.profileImage = profileImage;
        this.email = email;
        this.name = name;
        this.phone = phone;
    }
}
