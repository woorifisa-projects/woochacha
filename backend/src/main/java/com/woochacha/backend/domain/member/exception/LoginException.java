package com.woochacha.backend.domain.member.exception;

import com.woochacha.backend.domain.member.dto.LoginResponseDto;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;

public class LoginException {
    public static LoginResponseDto exception(Exception e) {
        if (e instanceof BadCredentialsException) {
            return new LoginResponseDto(2, "없는 아이디 또는 비밀번호", null, null);
        } else if (e instanceof DisabledException) {
            return new LoginResponseDto(3, "사용 불가 계정", null, null);
        } else if (e instanceof LockedException) {
            return new LoginResponseDto(4, "잠긴 계정", null, null);
        } else if (e instanceof AccountExpiredException) {
            return new LoginResponseDto(5, "만료된 계정", null, null);
        } else if (e instanceof CredentialsExpiredException) {
            return new LoginResponseDto(6, "비밀번호가 만료된 계정", null, null);
        } else if (e instanceof AuthenticationException) {
            return new LoginResponseDto(7, "시스템 에러", null, null);
        } else {
            return new LoginResponseDto(8, "찾을 수 없는 계정", null, null);
        }
    }
}
