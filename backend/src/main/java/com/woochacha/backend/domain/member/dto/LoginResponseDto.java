package com.woochacha.backend.domain.member.dto;

import lombok.*;

//@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class LoginResponseDto {
    private int code;
    private String msg;
    private String token;
}
