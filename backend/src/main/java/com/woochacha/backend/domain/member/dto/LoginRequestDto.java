package com.woochacha.backend.domain.member.dto;

import lombok.Data;

@Data
public class LoginRequestDto {
    String email;
    String password;
}
