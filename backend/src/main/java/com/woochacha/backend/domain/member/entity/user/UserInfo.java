package com.woochacha.backend.domain.member.entity.user;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter
public class UserInfo {

    private Boolean role;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false, unique = true)
    private String password;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false, unique = true)
    private String phone;

    protected  UserInfo(){

    }

    public UserInfo(Boolean role, String email, String password, String username, String phone) {
        this.role = role;
        this.email = email;
        this.password = password;
        this.username = username;
        this.phone = phone;
    }
}
