package com.woochacha.backend.domain.member.dto;

import lombok.Data;

//@Data

@Data
public class SignUpRequestDto {
    private String email;
    private String password;
    private String name;
    private String phone;
}
