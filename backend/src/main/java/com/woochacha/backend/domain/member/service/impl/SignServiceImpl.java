package com.woochacha.backend.domain.member.service.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.woochacha.backend.common.CommonResponse;
<<<<<<< HEAD
import com.woochacha.backend.domain.jwt.JwtFilter;
import com.woochacha.backend.domain.member.dto.LoginRequestDto;
import com.woochacha.backend.domain.member.dto.SignUpRequestDto;
import com.woochacha.backend.domain.member.dto.SignUpResponseDto;
import com.woochacha.backend.domain.member.dto.LoginSuccessDto;
=======
import com.woochacha.backend.domain.member.dto.SignUpRequestDto;
import com.woochacha.backend.domain.member.dto.SignUpResponseDto;
>>>>>>> 32f9886682f2c2b121075c5bfc8026fb53aea5aa
import com.woochacha.backend.domain.member.entity.Member;
import com.woochacha.backend.domain.member.entity.QMember;
import com.woochacha.backend.domain.member.repository.MemberRepository;
import com.woochacha.backend.domain.member.service.SignService;
import com.woochacha.backend.domain.jwt.JwtTokenProvider;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
<<<<<<< HEAD
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
=======
>>>>>>> 32f9886682f2c2b121075c5bfc8026fb53aea5aa
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SignServiceImpl implements SignService {


    private final JPAQueryFactory queryFactory;

    private final Logger LOGGER = LoggerFactory.getLogger(SignServiceImpl.class);

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

<<<<<<< HEAD
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public SignServiceImpl(JPAQueryFactory queryFactory, MemberRepository memberRepository, JwtTokenProvider jwtTokenProvider,
                           PasswordEncoder passwordEncoder, AuthenticationManagerBuilder authenticationManagerBuilder) {
=======
    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public SignServiceImpl(JPAQueryFactory queryFactory, MemberRepository memberRepository, JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder) {
>>>>>>> 32f9886682f2c2b121075c5bfc8026fb53aea5aa
        this.queryFactory = queryFactory;
        this.memberRepository = memberRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
<<<<<<< HEAD
        this.authenticationManagerBuilder = authenticationManagerBuilder;
=======
>>>>>>> 32f9886682f2c2b121075c5bfc8026fb53aea5aa
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
                LOGGER.info("[getSignUpResult] 회원 가입 완료");
                setSuccessResult(signUpResponseDto);
            } else {
                LOGGER.info("[getSignUpResult] 회원 가입 실패");
                setFailResult(signUpResponseDto, CommonResponse.FAIL);
            }
        }
        return signUpResponseDto;
    }

<<<<<<< HEAD
    public ResponseEntity<LoginSuccessDto> login(LoginRequestDto loginRequestDto) throws BadCredentialsException {

        LOGGER.info("[UsernamePasswordAuthenticationToken] 토큰 생성 시작");
        // Authentication 토큰 생성
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword());
        LOGGER.info("[UsernamePasswordAuthenticationToken] 토큰 생성 끝");


        LOGGER.info("[Authentication] 시작");
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
    }

=======
>>>>>>> 32f9886682f2c2b121075c5bfc8026fb53aea5aa
    public Member save(SignUpRequestDto signUpRequestDto) {
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
