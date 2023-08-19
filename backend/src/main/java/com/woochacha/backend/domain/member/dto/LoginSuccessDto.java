package com.woochacha.backend.domain.member.dto;

import lombok.*;

//@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class LoginSuccessDto {
    private String token;
}
