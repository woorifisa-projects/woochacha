package com.woochacha.backend.domain.member.entity.member;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter
public class MemberInfo {

    private Boolean role;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String memberName;
    @Column(nullable = false, unique = true)
    private String phone;

    protected MemberInfo(){

    }

    public MemberInfo(Boolean role, String email, String password, String memberName, String phone) {
        this.role = role;
        this.email = email;
        this.password = password;
        this.memberName = memberName;
        this.phone = phone;
    }
}