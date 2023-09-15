package com.woochacha.backend.domain.sendSMS.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Builder
@NoArgsConstructor
@Table(name = "auth_phone")
public class AuthPhone {
    @Id
    @Column(name = "phone")
    private String phone;

    @Column(name = "auth_number")
    @NotNull
    private String authNumber;

    @Column(name = "auth_status")
    @NotNull
    @ColumnDefault("0")
    private Byte authStatus;

    public AuthPhone(String phone, String authNumber, Byte authStatus) {
        this.phone = phone;
        this.authNumber = authNumber;
        this.authStatus = authStatus;
    }
}
