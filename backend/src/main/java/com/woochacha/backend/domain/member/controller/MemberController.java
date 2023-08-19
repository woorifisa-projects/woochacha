package com.woochacha.backend.domain.member.controller;

<<<<<<< HEAD
import com.woochacha.backend.domain.jwt.JwtFilter;
import com.woochacha.backend.domain.jwt.JwtTokenProvider;
import com.woochacha.backend.domain.member.dto.LoginRequestDto;
import com.woochacha.backend.domain.member.dto.SignUpRequestDto;
import com.woochacha.backend.domain.member.dto.SignUpResponseDto;
import com.woochacha.backend.domain.member.dto.LoginSuccessDto;
import com.woochacha.backend.domain.member.service.SignService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
=======
import com.woochacha.backend.domain.member.dto.LoginRequestDto;
import com.woochacha.backend.domain.member.dto.SignUpRequestDto;
import com.woochacha.backend.domain.member.dto.SignUpResponseDto;
import com.woochacha.backend.domain.member.service.SignService;
>>>>>>> 32f9886682f2c2b121075c5bfc8026fb53aea5aa
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class MemberController {

<<<<<<< HEAD
    private SignService signService;

    private Logger LOGGER = LoggerFactory.getLogger(MemberController.class);

    private final JwtTokenProvider jwtTokenProvider;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public MemberController(JwtTokenProvider jwtTokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
=======
    private SignService memberService;

    public MemberController(SignService memberService) {
        this.memberService = memberService;
>>>>>>> 32f9886682f2c2b121075c5bfc8026fb53aea5aa
    }

    @GetMapping
    public String mainPage() {
        return "redirect:/";
    }

    @PostMapping("/register")
    public SignUpResponseDto registerUser(@Valid @RequestBody SignUpRequestDto memberRequestDto){
<<<<<<< HEAD
        return signService.signUp(memberRequestDto);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginSuccessDto> login(@RequestBody LoginRequestDto loginRequestDto) {
//        return signService.login(loginRequestDto);

        // Authentication 토큰 생성
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword());

        LOGGER.info("[Authentication] 시작");
        try {
            // loadUserByUsername 메소드 실행됨
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        LOGGER.info("[Authentication] 끝");

//        if(authentication.getCredentials() == null) {
//            throw new BadCredentialsException("### 없는 이메일");
//        }

        LOGGER.info("[setAuthentication] 시작");
        // Authentication 객체 생성 -> SecurityContext에 저장
        SecurityContextHolder.getContext().setAuthentication(authentication);
        LOGGER.info("[setAuthentication] 끝");

        // Authentication 객체를 createToken 메소드를 통해서 JWT Token을 생성
        String jwt = jwtTokenProvider.createToken(authentication);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);




        return new ResponseEntity<>(new LoginSuccessDto(jwt), httpHeaders, HttpStatus.OK);
        } catch (BadCredentialsException e) {
            LOGGER.error("[ERROR] in catch");
            throw new BadCredentialsException("[BadCredentialsException Error]");
        }

    }
=======
        return memberService.signUp(memberRequestDto);
    }

//    @PostMapping("/login")
//    public String login(@RequestBody LoginRequestDto loginRequestDto) {
//        System.out.println(loginRequestDto.getEmail());
//        // 사용자 인증 처리
//        return "redirect:/product";
//    }
>>>>>>> 32f9886682f2c2b121075c5bfc8026fb53aea5aa

}
