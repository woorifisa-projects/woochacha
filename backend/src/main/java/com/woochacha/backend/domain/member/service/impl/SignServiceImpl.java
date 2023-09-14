package com.woochacha.backend.domain.member.service.impl;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.woochacha.backend.common.ModelMapping;
import com.woochacha.backend.domain.jwt.JwtAuthenticationFilter;
import com.woochacha.backend.domain.jwt.JwtTokenProvider;
import com.woochacha.backend.domain.log.service.impl.LogServiceImpl;
import com.woochacha.backend.domain.member.dto.LoginRequestDto;
import com.woochacha.backend.domain.member.dto.LoginResponseDto;
import com.woochacha.backend.domain.member.dto.SignResponseDto;
import com.woochacha.backend.domain.member.dto.SignUpRequestDto;
import com.woochacha.backend.domain.member.entity.Member;
import com.woochacha.backend.domain.member.entity.QMember;
import com.woochacha.backend.domain.member.exception.LoginException;
import com.woochacha.backend.domain.member.exception.SignException;
import com.woochacha.backend.domain.member.exception.SignResultCode;
import com.woochacha.backend.domain.member.repository.MemberRepository;
import com.woochacha.backend.domain.member.service.SignService;
import com.woochacha.backend.domain.product.entity.QProduct;
import com.woochacha.backend.domain.sale.entity.QSaleForm;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SignServiceImpl implements SignService {

    private final JPAQueryFactory queryFactory;

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    private final QMember m = QMember.member;

    private final QProduct p = QProduct.product;

    private final QSaleForm sf = QSaleForm.saleForm;

    private final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final ModelMapper modelMapper = ModelMapping.getInstance();

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final LogServiceImpl logService;

    public SignServiceImpl(JPAQueryFactory queryFactory, MemberRepository memberRepository, JwtTokenProvider jwtTokenProvider,
                           PasswordEncoder passwordEncoder, AuthenticationManagerBuilder authenticationManagerBuilder, LogServiceImpl logService) {
        this.queryFactory = queryFactory;
        this.memberRepository = memberRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.logService = logService;
    }

    /*
        [회원가입]
        @Params : signUpRequestDto - email, name, phone, password
     */
    @Transactional
    @Override
    public SignResponseDto signUp(SignUpRequestDto signUpRequestDto) {
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
                    logger.debug("phone:{} 회원가입 실패 : 이용 정지를 당한 회원이 탈퇴 후 재가입 시도", signUpRequestDto.getPhone());
                    return SignException.exception(SignResultCode.SUSPENDED_ACCOUNT);
                }
            }

            // 탈퇴하지 않은 이미 존재하는 회원인 경우 실패
            for(Member phoneSearch : phoneSearchList) {
                if(phoneSearch.isEnabled()) {
                    logger.debug("phone:{} 회원가입 실패 : 이미 존재하는 회원", signUpRequestDto.getPhone());
                    return SignException.exception(SignResultCode.DUPLICATE_PHONE);
                }
            }
        }

        // 이메일 중복인 회원 리스트
        else if (!emailSearchList.isEmpty()) {
            // 이용 정지를 당한 같은 이메일을 가진 회원이 탈퇴 후 재가입하는 경우 실패
            for(Member emailSearch : emailSearchList) {
                if(!emailSearch.isAccountNonLocked()) {
                    logger.debug("email:{} 회원가입 실패 : 이용 정지를 당한 회원이 탈퇴 후 재가입 시도", signUpRequestDto.getEmail());
                    return SignException.exception(SignResultCode.SUSPENDED_ACCOUNT);
                }
            }

            // 탈퇴하지 않은 이미 존재하는 회원인 경우 실패
            for(Member emailSearch : emailSearchList) {
                if(emailSearch.isEnabled()) {
                    logger.debug("email:{} 회원가입 실패 : 이미 존재하는 회원", signUpRequestDto.getEmail());
                    return SignException.exception(SignResultCode.DUPLICATE_EMAIL);
                }
            }
        }

        // 회원가입 시 member의 기본 프로필 사진 설정
        signUpRequestDto.setProfileImage("https://woochacha.s3.ap-northeast-2.amazonaws.com/profile/default");

        // Member 테이블에 회원 정보 저장
        Member savedMember = save(signUpRequestDto);
        logger.info("사용자 회원가입 memberId:{}", savedMember.getId());

        if (!savedMember.getName().isEmpty()) {
            logger.debug("회원가입 중 이름 미입력");
            return SignException.exception(SignResultCode.SUCCESS);
        } else {
            logger.debug("회원가입 실패");
            return SignException.exception(SignResultCode.FAIL);
        }

    }

    @Transactional
    @Override
    public LoginResponseDto login(LoginRequestDto loginRequestDto) throws BadCredentialsException {
        try {
            Optional<Member> memberOptional = Optional.ofNullable(queryFactory
                    .selectFrom(m)
                    .where(m.id.eq(JPAExpressions.select(m.id.max()).from(m).where(m.email.eq(loginRequestDto.getEmail()))))
                    .fetchOne());
            if(memberOptional.isEmpty()) throw new BadCredentialsException("사용자 없음");

            Member member = memberOptional.get();

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(member.getEmail(), loginRequestDto.getPassword());

            // loadUserByUsername 메소드 실행됨
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

            // Authentication 객체를 createToken 메서드를 통해서 JWT Token 생성
            String jwt = jwtTokenProvider.createToken(authentication);

            logService.savedMemberLogWithType(member.getId(), "로그인");

            return new LoginResponseDto(1, "성공", jwt, member.getId(), member.getName());
        } catch (Exception e) {
            return LoginException.exception(e);
        }
    }

    @Override
    @Transactional
    public boolean logout(Long memberId) {
        // 로그아웃 로그 저장
        logService.savedMemberLogWithType(memberId, "로그아웃");
        return true;
    }

    @Override
    @Transactional
    public SignResponseDto signOut(Long memberId, HttpServletRequest request) {
        // header의 token에서 member id를 꺼냄
        // 탈퇴를 요청한 member id와 입력 받은 member id가 동일하면 탈퇴 승인. 다르면 403 에러.
        String memberName = jwtTokenProvider.getUserName(parseJwt(request));

        Long findMemberId = queryFactory
                .select(m.id)
                .from(m)
                .where(m.email.eq(memberName), m.status.eq((byte) 1))
                .fetchOne();


        if(!Objects.equals(findMemberId, memberId))
            return SignException.exception(SignResultCode.ACCESS_DENIED,"탈퇴 요청 권한 없음"); // access denied

        queryFactory
                .update(m)
                .set(m.status, (byte) 0)
                .where(m.id.eq(memberId))
                .execute();


        List<Long> productIdList = queryFactory
                .select(p.id)
                .from(p).join(sf).on(p.saleForm.id.eq(sf.id))
                .where(sf.member.id.eq(memberId))
                .fetch();

        for(Long productId : productIdList) {
            memberRepository.updateProductSuccessStatus(productId);
        }

        // 회원 탈퇴 로그 저장
        logService.savedMemberLogWithType(memberId, "회원 탈퇴");
        return SignException.exception(SignResultCode.SUCCESS);
    }

    private Member save(SignUpRequestDto signUpRequestDto) {
        signUpRequestDto.setPassword(passwordEncoder.encode(signUpRequestDto.getPassword()));
        Member savedMember = modelMapper.map(signUpRequestDto, Member.class);
        savedMember.getRoles().add("USER");
        memberRepository.save(savedMember);
        return savedMember;
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }
        return null;
    }
}