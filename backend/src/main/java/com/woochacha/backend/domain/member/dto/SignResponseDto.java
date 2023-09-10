package com.woochacha.backend.domain.member.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Builder
@Getter
@Setter
public class SignResponseDto {
    private boolean success;
    private int code;
    private String msg;

    public SignResponseDto() {}
    public SignResponseDto(boolean success, int code, String msg) {
        this.success = success;
        this.code = code;
        this.msg = msg;
    }
}