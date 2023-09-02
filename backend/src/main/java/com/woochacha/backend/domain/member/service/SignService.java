package com.woochacha.backend.domain.member.service;

import com.woochacha.backend.domain.member.dto.*;

public interface SignService {
    SignUpResponseDto signUp(SignUpRequestDto userRequestDto);

    LoginResponseDto login(LoginRequestDto loginRequestDto);

    boolean logout();
}