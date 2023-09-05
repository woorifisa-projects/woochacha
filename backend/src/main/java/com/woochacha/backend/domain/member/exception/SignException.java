package com.woochacha.backend.domain.member.exception;

import com.woochacha.backend.domain.member.dto.SignResponseDto;

public class SignException {
    public static SignResponseDto exception(SignResultCode commonResponse) {
        SignResponseDto signResponseDto = exceptionCommon(commonResponse);
        signResponseDto.setMsg(commonResponse.getMsg());
        return signResponseDto;
    }

    public static SignResponseDto exception(SignResultCode commonResponse, String msg) {
        SignResponseDto signResponseDto = exceptionCommon(commonResponse);
        signResponseDto.setMsg(msg);
        return signResponseDto;
    }

    private static SignResponseDto exceptionCommon(SignResultCode commonResponse) {
        SignResponseDto SignResponseDto = new SignResponseDto();
        SignResponseDto.setCode(commonResponse.getCode());

        if (commonResponse == SignResultCode.SUCCESS) {
            SignResponseDto.setSuccess(true);
        } else {
            SignResponseDto.setSuccess(false);
        }

        return SignResponseDto;
    }
}
