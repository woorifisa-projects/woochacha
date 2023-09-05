package com.woochacha.backend.domain.member.controller;

import com.woochacha.backend.domain.log.service.LogService;
import com.woochacha.backend.domain.member.dto.LoginRequestDto;
import com.woochacha.backend.domain.member.dto.LoginResponseDto;
import com.woochacha.backend.domain.member.dto.SignUpRequestDto;
import com.woochacha.backend.domain.member.dto.SignUpResponseDto;
import com.woochacha.backend.domain.member.service.SignService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class MemberController {

    private final SignService signService;

    private final LogService logService;

    public MemberController(SignService signService, LogService logService) {
        this.signService = signService;
        this.logService = logService;
    }

    @PostMapping("/register")
    public SignUpResponseDto registerUser(@Valid @RequestBody SignUpRequestDto memberRequestDto){
        return signService.signUp(memberRequestDto);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletRequest request) {
        LoginResponseDto loginResponseDto = signService.login(loginRequestDto);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", loginResponseDto.getToken());

        logService.savedMemberLogWithType(loginResponseDto.getId(), "로그인");

        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(loginResponseDto);
    }

    @PostMapping("/logout")
    public ResponseEntity<Boolean> logout(@RequestParam("memberId") Long memberId) {
        if (signService.logout(memberId)) return ResponseEntity.ok(true);
        return ResponseEntity.ok(false);
    }
}
