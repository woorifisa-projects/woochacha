package com.woochacha.backend.domain.member.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class LoginFailDto {
    int code;
    String msg;
}
