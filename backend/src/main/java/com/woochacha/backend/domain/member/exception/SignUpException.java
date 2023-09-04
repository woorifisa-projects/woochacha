package com.woochacha.backend.domain.member.exception;

import com.woochacha.backend.domain.member.dto.SignUpResponseDto;

public class SignUpException {
    public static SignUpResponseDto exception(SignResultCode commonResponse) {
        SignUpResponseDto signUpResponseDto = new SignUpResponseDto();
        signUpResponseDto.setCode(commonResponse.getCode());
        signUpResponseDto.setMsg(commonResponse.getMsg());

        if (commonResponse == SignResultCode.SUCCESS) {
            signUpResponseDto.setSuccess(true);
        } else {
            signUpResponseDto.setSuccess(false);
        }

        return signUpResponseDto;
    }
}
