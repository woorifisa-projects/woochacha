package dev.market.spring_market.dto;


import lombok.Getter;

@Getter
public class LoginReq {
    private String userEmail;
    private String password;
}
