package dev.market.spring_market.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LoginRes {
    private Long userId;
    private String userEmail;
    private String nickname;
    private char gender;
}
