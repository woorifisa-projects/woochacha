package com.woochacha.backend.domain.member.controller;

import com.woochacha.backend.domain.jwt.JwtFilter;
import com.woochacha.backend.domain.member.dto.LoginRequestDto;
import com.woochacha.backend.domain.member.dto.LoginResponseDto;
import com.woochacha.backend.domain.member.dto.SignUpRequestDto;
import com.woochacha.backend.domain.member.dto.SignUpResponseDto;
import com.woochacha.backend.domain.member.service.SignService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class MemberController {

    private final SignService signService;

    public MemberController(SignService signService) {
        this.signService = signService;
    }

    @GetMapping
    public String mainPage() {
        return "redirect:/";
    }

    @PostMapping("/register")
    public SignUpResponseDto registerUser(@Valid @RequestBody SignUpRequestDto memberRequestDto){
        return signService.signUp(memberRequestDto);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        LoginResponseDto loginResponseDto = signService.login(loginRequestDto);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, loginResponseDto.getToken());
        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(loginResponseDto);
    }

    @PostMapping("/logout")
    public boolean logout() {
        return signService.logout();
    }


}
