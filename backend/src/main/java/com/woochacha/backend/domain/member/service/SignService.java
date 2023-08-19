package com.woochacha.backend.domain.member.service;

import com.woochacha.backend.domain.member.dto.LoginRequestDto;
import com.woochacha.backend.domain.member.dto.SignUpRequestDto;
import com.woochacha.backend.domain.member.dto.SignUpResponseDto;
import com.woochacha.backend.domain.member.dto.LoginSuccessDto;
import org.springframework.http.ResponseEntity;

public interface SignService {
    SignUpResponseDto signUp(SignUpRequestDto userRequestDto);

    ResponseEntity<LoginSuccessDto> login(LoginRequestDto loginRequestDto);
}
