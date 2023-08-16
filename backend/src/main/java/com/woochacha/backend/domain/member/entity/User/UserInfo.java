package com.woochacha.backend.domain.member.entity.User;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class UserInfo {
    private Boolean role;
    private String email;
    private String password;
    private String nickname;
    private String phone;

    protected  UserInfo(){

    }

    public UserInfo(Boolean role, String email, String password, String nickname, String phone) {
        this.role = role;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.phone = phone;
    }
}
