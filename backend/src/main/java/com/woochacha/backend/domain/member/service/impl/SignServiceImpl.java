package com.woochacha.backend.domain.member.service.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.woochacha.backend.common.CommonResponse;
import com.woochacha.backend.domain.member.dto.SignUpRequestDto;
import com.woochacha.backend.domain.member.dto.SignUpResponseDto;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SignServiceImpl implements SignService {


//    private final JPAQueryFactory queryFactory;

    private final Logger LOGGER = LoggerFactory.getLogger(SignServiceImpl.class);

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public SignServiceImpl(JPAQueryFactory queryFactory, MemberRepository memberRepository, JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder) {
        this.queryFactory = queryFactory;
        this.memberRepository = memberRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
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
