package com.woochacha.backend.domain.member.exception;

import com.woochacha.backend.common.CommonResponse;
import com.woochacha.backend.domain.member.dto.SignUpResponseDto;

public class SignUpException {
    public static SignUpResponseDto exception(CommonResponse commonResponse) {
        SignUpResponseDto signUpResponseDto = new SignUpResponseDto();
        signUpResponseDto.setCode(commonResponse.getCode());
        signUpResponseDto.setMsg(commonResponse.getMsg());

        if (commonResponse == CommonResponse.SUCCESS) {
            signUpResponseDto.setSuccess(true);
        } else {
            signUpResponseDto.setSuccess(false);
        }

        return signUpResponseDto;
    }
}
