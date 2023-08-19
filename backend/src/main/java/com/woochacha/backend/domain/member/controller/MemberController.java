package com.woochacha.backend.domain.member.controller;

import com.woochacha.backend.domain.member.dto.LoginRequestDto;
import com.woochacha.backend.domain.member.dto.SignUpRequestDto;
import com.woochacha.backend.domain.member.dto.SignUpResponseDto;
import com.woochacha.backend.domain.member.service.SignService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class MemberController {

    private SignService memberService;

    public MemberController(SignService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public String mainPage() {
        return "redirect:/";
    }

    @PostMapping("/register")
    public SignUpResponseDto registerUser(@Valid @RequestBody SignUpRequestDto memberRequestDto){
        return memberService.signUp(memberRequestDto);
    }

//    @PostMapping("/login")
//    public String login(@RequestBody LoginRequestDto loginRequestDto) {
//        System.out.println(loginRequestDto.getEmail());
//        // 사용자 인증 처리
//        return "redirect:/product";
//    }

}
