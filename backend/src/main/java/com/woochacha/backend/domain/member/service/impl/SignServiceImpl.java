package com.woochacha.backend.domain.member.service.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.woochacha.backend.common.CommonResponse;
import com.woochacha.backend.common.ModelMapping;
import com.woochacha.backend.domain.jwt.JwtAuthenticationFilter;
import com.woochacha.backend.domain.jwt.JwtFilter;
import com.woochacha.backend.domain.jwt.JwtTokenProvider;
import com.woochacha.backend.domain.member.dto.LoginRequestDto;
import com.woochacha.backend.domain.member.dto.LoginResponseDto;
import com.woochacha.backend.domain.member.dto.SignUpRequestDto;
import com.woochacha.backend.domain.member.dto.SignUpResponseDto;
import com.woochacha.backend.domain.member.entity.Member;
import com.woochacha.backend.domain.member.entity.QMember;
import com.woochacha.backend.domain.member.exception.LoginException;
import com.woochacha.backend.domain.member.repository.MemberRepository;
import com.woochacha.backend.domain.member.service.SignService;
import com.woochacha.backend.domain.product.entity.QCarImage;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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
        this.modelMapperInit(modelMapper);
    }

    /*
        [회원가입]
        @Params : signUpRequestDto - email, name, phone, password
     */
    public SignUpResponseDto signUp(SignUpRequestDto signUpRequestDto) {

        SignUpResponseDto signUpResponseDto = new SignUpResponseDto();

        // 이메일 중복 체크
        List<String> emailSearch = queryFactory
                .select(QMember.member.email)
                .from(QMember.member)
                .where(QMember.member.email.eq(signUpRequestDto.getEmail()))
                .fetch();

        // 핸드폰 번호 중복 체크
        List<String> phoneSearch = queryFactory
                .select(QMember.member.phone)
                .from(QMember.member)
                .where(QMember.member.phone.eq(signUpRequestDto.getPhone()))
                .fetch();

        if(!emailSearch.isEmpty()) { // 이메일 중복
            setFailResult(signUpResponseDto, CommonResponse.DUPLICATE_EMAIL_EXCEPTION);
        } else if(!phoneSearch.isEmpty()) { // 핸드폰 번호 중복
            setFailResult(signUpResponseDto, CommonResponse.DUPLICATE_PHONE_EXCEPTION);
        } else {
            // Member 테이블에 회원 정보 저장
            Member savedMember = save(signUpRequestDto);

            if (!savedMember.getName().isEmpty()) {
                setSuccessResult(signUpResponseDto);
            } else {
                setFailResult(signUpResponseDto, CommonResponse.FAIL);
            }
        }
        return signUpResponseDto;
    }

    public LoginResponseDto login(LoginRequestDto loginRequestDto) throws BadCredentialsException {
        try {
            // Authentication 토큰 생성
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword());

            // loadUserByUsername 메소드 실행됨
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

            // Authentication 객체 생성 -> SecurityContext에 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Authentication 객체를 createToken 메소드를 통해서 JWT Token을 생성
            String jwt = jwtTokenProvider.createToken(authentication);

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

            return new LoginResponseDto(1, "성공", jwt);
        } catch (Exception e) {
            return LoginException.exception(e);
        }
    }

    public boolean logout() {

        return true;
    }


    public Member save(SignUpRequestDto signUpRequestDto) {

        LOGGER.info(modelMapper.toString());
        signUpRequestDto.setPassword(passwordEncoder.encode(signUpRequestDto.getPassword()));
        Member savedMember = modelMapper.map(signUpRequestDto, Member.class);
        memberRepository.save(savedMember);
        return savedMember;
    }

    // 결과 모델에 api 요청 성공 데이터를 세팅해주는 메소드
    private void setSuccessResult(SignUpResponseDto result) {
        result.setSuccess(true);
        result.setCode(CommonResponse.SUCCESS.getCode());
        result.setMsg(CommonResponse.SUCCESS.getMsg());
    }

    // 결과 모델에 api 요청 실패 데이터를 세팅해주는 메소드
    private void setFailResult(SignUpResponseDto result, CommonResponse commonResponse) {
        result.setSuccess(false);
        result.setCode(commonResponse.getCode());
        result.setMsg(commonResponse.getMsg());
    }

    private void modelMapperInit(ModelMapper modelMapper) {
        modelMapper.getConfiguration()
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE)
                .setFieldMatchingEnabled(true);
    }


}
