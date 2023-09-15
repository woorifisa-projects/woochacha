package com.woochacha.backend.domain.member.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AuthPhoneRequestDto {
    private String phone;
    private String authNum;
}
