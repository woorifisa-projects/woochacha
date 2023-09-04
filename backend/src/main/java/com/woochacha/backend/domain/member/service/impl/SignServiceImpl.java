package com.woochacha.backend.domain.member.service.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.woochacha.backend.domain.member.entity.QMember;
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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
//@Transactional(readOnly = true)
public class SignServiceImpl implements SignService {

    private final JPAQueryFactory queryFactory;

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    private final QMember m = QMember.member;

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
        // 핸드폰 번호 중복 체크
        List<Member> phoneSearchList = queryFactory
                .selectFrom(m)
                .where(m.phone.eq(signUpRequestDto.getPhone()))
                .fetch();

        // 이메일 중복 체크
        List<Member> emailSearchList = queryFactory
                .selectFrom(m)
                .where(m.email.eq(signUpRequestDto.getEmail()))
                .fetch();


        // 핸드폰 번호 중복인 회원 리스트
        if (!phoneSearchList.isEmpty()) {
            // 이용 정지를 당한 같은 핸드폰 번호를 가진 회원이 탈퇴 후 재가입하는 경우 실패
            for(Member phoneSearch : phoneSearchList) {
                if(!phoneSearch.isAccountNonLocked()) {
                    return SignUpException.exception(SignResultCode.SUSPENDED_ACCOUNT);
                }
            }

            // 탈퇴하지 않은 이미 존재하는 회원인 경우 실패
            for(Member phoneSearch : phoneSearchList) {
                if(phoneSearch.isEnabled()) {
                    return SignUpException.exception(SignResultCode.DUPLICATE_PHONE);
                }
            }
        }

        // 이메일 중복인 회원 리스트
        else if (!emailSearchList.isEmpty()) {
            // 이용 정지를 당한 같은 이메일을 가진 회원이 탈퇴 후 재가입하는 경우 실패
            for(Member emailSearch : emailSearchList) {
                if(!emailSearch.isAccountNonLocked()) {
                    return SignUpException.exception(SignResultCode.SUSPENDED_ACCOUNT);
                }
            }

            // 탈퇴하지 않은 이미 존재하는 회원인 경우 실패
            for(Member emailSearch : emailSearchList) {
                if(emailSearch.isEnabled()) {
                    return SignUpException.exception(SignResultCode.DUPLICATE_EMAIL);
                }
            }
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
            Optional<Member> memberOptional = Optional.ofNullable(queryFactory
                    .selectFrom(m)
                    .where(m.email.eq(loginRequestDto.getEmail()), m.id.eq(JPAExpressions.select(m.id.max()).from(m)))
                    .fetchOne());
            if(memberOptional.isEmpty()) throw new BadCredentialsException("사용자 없음");

            Member member = memberOptional.get();

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