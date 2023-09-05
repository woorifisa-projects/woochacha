package com.woochacha.backend.domain.member.exception;

import com.woochacha.backend.domain.member.dto.SignResponseDto;

public class SignException {
    public static SignResponseDto exception(SignResultCode commonResponse) {
        SignResponseDto SignResponseDto = new SignResponseDto();
        SignResponseDto.setCode(commonResponse.getCode());
        SignResponseDto.setMsg(commonResponse.getMsg());

        if (commonResponse == SignResultCode.SUCCESS) {
            SignResponseDto.setSuccess(true);
        } else {
            SignResponseDto.setSuccess(false);
        }

        return SignResponseDto;
    }

    public static SignResponseDto exception(SignResultCode commonResponse, String msg) {
        SignResponseDto SignResponseDto = new SignResponseDto();
        SignResponseDto.setCode(commonResponse.getCode());
        SignResponseDto.setMsg(msg);

        if (commonResponse == SignResultCode.SUCCESS) {
            SignResponseDto.setSuccess(true);
        } else {
            SignResponseDto.setSuccess(false);
        }

        return SignResponseDto;
    }
}
