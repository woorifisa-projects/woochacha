package com.woochacha.backend.domain.member.service;

import com.woochacha.backend.domain.member.dto.SignUpRequestDto;
import com.woochacha.backend.domain.member.dto.SignUpResponseDto;
import com.woochacha.backend.domain.member.entity.Member;

public interface SignService {
    SignUpResponseDto signUp(SignUpRequestDto userRequestDto);
}
