package com.woochacha.backend.domain.member.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.woochacha.backend.domain.member.dto.*;
import com.woochacha.backend.domain.member.service.SignService;
import com.woochacha.backend.domain.sendSMS.sendMessage.MessageDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/users")
public class MemberController{

    private final SignService signService;

    public MemberController(SignService signService) {
        this.signService = signService;
    }

    @PostMapping("/auth")
    public String authPhone(@RequestBody PhoneRequestDto phoneRequestDto) throws UnsupportedEncodingException, NoSuchAlgorithmException, URISyntaxException, InvalidKeyException, JsonProcessingException {
        return signService.authPhone(phoneRequestDto);
    }
    @PatchMapping ("/auth")
    public String checkAuthPhone(@RequestBody AuthPhoneRequestDto authPhoneRequestDto){
        return signService.checkAuthPhone(authPhoneRequestDto);
    }

    @PostMapping("/register")
    public SignResponseDto registerUser(@Valid @RequestBody SignUpRequestDto memberRequestDto){
        return signService.signUp(memberRequestDto);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        LoginResponseDto loginResponseDto = signService.login(loginRequestDto);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", loginResponseDto.getToken());

        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(loginResponseDto);
    }

    @PostMapping("/logout")
    public ResponseEntity<Boolean> logout(@RequestParam("memberId") Long memberId) {
        if (signService.logout(memberId)) return ResponseEntity.ok(true);
        return ResponseEntity.ok(false);
    }

    @PatchMapping("/signout")
    public ResponseEntity<SignResponseDto> signOut(@RequestParam("memberId") Long memberId, HttpServletRequest request) {
        SignResponseDto signResponseDto = signService.signOut(memberId, request);
        return ResponseEntity.ok().body(signResponseDto);
    }
}
