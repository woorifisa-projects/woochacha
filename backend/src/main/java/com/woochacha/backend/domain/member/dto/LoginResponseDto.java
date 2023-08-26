package com.woochacha.backend.domain.member.dto;

import lombok.*;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class LoginResponseDto {
    private int code;
    private String msg;
    private String token;
    private Long id;
    private String name;

    public LoginResponseDto(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
