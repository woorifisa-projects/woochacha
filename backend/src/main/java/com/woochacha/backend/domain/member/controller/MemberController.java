package com.woochacha.backend.domain.member.controller;

import com.woochacha.backend.domain.member.dto.LoginRequestDto;
import com.woochacha.backend.domain.member.dto.LoginResponseDto;
import com.woochacha.backend.domain.member.dto.SignUpRequestDto;
import com.woochacha.backend.domain.member.dto.SignUpResponseDto;
import com.woochacha.backend.domain.member.service.SignService;
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
    public LoginResponseDto login(@RequestBody LoginRequestDto loginRequestDto) {
        return signService.login(loginRequestDto);
    }

    @PostMapping("/logout")
    public boolean logout() {
        return signService.logout();
    }


}
