package com.woochacha.backend.domain.member.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.woochacha.backend.domain.member.dto.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface SignService {
    String authPhone(PhoneRequestDto phoneRequestDto) throws UnsupportedEncodingException, NoSuchAlgorithmException, URISyntaxException, InvalidKeyException, JsonProcessingException;
    SignResponseDto signUp(SignUpRequestDto userRequestDto);

    LoginResponseDto login(LoginRequestDto loginRequestDto);

    boolean logout(Long memberId);
    SignResponseDto signOut(Long memberId, HttpServletRequest request);
    String checkAuthPhone(AuthPhoneRequestDto authPhoneRequestDto);
}