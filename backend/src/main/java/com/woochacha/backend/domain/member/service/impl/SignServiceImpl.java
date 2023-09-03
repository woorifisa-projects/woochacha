package com.woochacha.backend.domain.member.service.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.woochacha.backend.domain.member.exception.SignResultCode;
import com.woochacha.backend.common.ModelMapping;
import com.woochacha.backend.domain.jwt.JwtAuthenticationFilter;
import com.woochacha.backend.domain.jwt.JwtTokenProvider;
import com.woochacha.backend.domain.member.dto.LoginRequestDto;
import com.woochacha.backend.domain.member.dto.LoginResponseDto;
import com.woochacha.backend.domain.member.dto.SignUpRequestDto;
import com.woochacha.backend.domain.member.dto.SignUpResponseDto;
import com.woochacha.backend.domain.member.entity.Member;
import com.woochacha.backend.domain.member.exception.LoginException;
import com.woochacha.backend.domain.member.exception.SignUpException;
import com.woochacha.backend.domain.member.repository.MemberRepository;
import com.woochacha.backend.domain.member.service.SignService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
//@Transactional(readOnly = true)
public class SignServiceImpl implements SignService {

    private final JPAQueryFactory queryFactory;

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    private final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final ModelMapper modelMapper = ModelMapping.getInstance();

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public SignServiceImpl(JPAQueryFactory queryFactory, MemberRepository memberRepository, JwtTokenProvider jwtTokenProvider,
                           PasswordEncoder passwordEncoder, AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.queryFactory = queryFactory;
        this.memberRepository = memberRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }

    /*
        [회원가입]
        @Params : signUpRequestDto - email, name, phone, password
     */
    @Transactional
    @Override
    public SignUpResponseDto signUp(SignUpRequestDto signUpRequestDto) {
        // 이메일 중복 체크
        Optional<Member> emailSearch = memberRepository.findByEmail(signUpRequestDto.getEmail());

        // 핸드폰 번호 중복 체크
        Member phoneSearch = memberRepository.findByPhone(signUpRequestDto.getPhone());

        //         TODO : 회원가입 조건 추가
//        회원 가입시 phone number을 비교하여 is_available을 먼저 검토하여 0이면 가입 불가
//        (is_available == 1)&&(status == 1)인 회원은 이미 가입된 회원이라는 코드 전달

        if (emailSearch.isPresent()) { // 이메일 중복
            return SignUpException.exception(SignResultCode.DUPLICATE_EMAIL_EXCEPTION);
        } else if (phoneSearch != null) { // 핸드폰 번호 중복
            return SignUpException.exception(SignResultCode.DUPLICATE_PHONE_EXCEPTION);
        }

        // 회원가입 시 member의 기본 프로필 사진 설정
        signUpRequestDto.setProfileImage("https://woochacha.s3.ap-northeast-2.amazonaws.com/profile/default");

        // Member 테이블에 회원 정보 저장
        Member savedMember = save(signUpRequestDto);

        if (!savedMember.getName().isEmpty()) {
            return SignUpException.exception(SignResultCode.SUCCESS);
        } else {
            return SignUpException.exception(SignResultCode.FAIL);
        }

    }

    @Transactional
    @Override
    public LoginResponseDto login(LoginRequestDto loginRequestDto) throws BadCredentialsException {
        try {
            Member member = memberRepository.findByEmail(loginRequestDto.getEmail())
                    .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(member.getEmail(), loginRequestDto.getPassword());

            // loadUserByUsername 메소드 실행됨
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

            // Authentication 객체를 createToken 메서드를 통해서 JWT Token 생성
            String jwt = jwtTokenProvider.createToken(authentication);
            return new LoginResponseDto(1, "성공", jwt, member.getId(), member.getName());
        } catch (Exception e) {
            return LoginException.exception(e);
        }
    }


    public boolean logout() {

        return true;
    }


    public Member save(SignUpRequestDto signUpRequestDto) {
        signUpRequestDto.setPassword(passwordEncoder.encode(signUpRequestDto.getPassword()));
        Member savedMember = modelMapper.map(signUpRequestDto, Member.class);
        savedMember.getRoles().add("USER");
        memberRepository.save(savedMember);
        return savedMember;
    }
}