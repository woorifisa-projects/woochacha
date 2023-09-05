package com.woochacha.backend.domain.member.service;

import com.woochacha.backend.domain.member.dto.LoginRequestDto;
import com.woochacha.backend.domain.member.dto.LoginResponseDto;
import com.woochacha.backend.domain.member.dto.SignUpRequestDto;
import com.woochacha.backend.domain.member.dto.SignResponseDto;

import javax.servlet.http.HttpServletRequest;

public interface SignService {
    SignResponseDto signUp(SignUpRequestDto userRequestDto);

    LoginResponseDto login(LoginRequestDto loginRequestDto);

    boolean logout(Long memberId);
    SignResponseDto signOut(Long memberId, HttpServletRequest request);
}