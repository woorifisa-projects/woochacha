package com.woochacha.backend.domain.member.dto;

import lombok.*;


@RequiredArgsConstructor
@Builder
@Getter
@Setter
public class SignUpRequestDto {
    private String email;
    private String password;
    private String name;
    private String phone;
    private String profileImage;

    public SignUpRequestDto(String email, String password, String name, String phone, String profileImage) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.profileImage = profileImage;
    }
}
