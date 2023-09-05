package com.woochacha.backend.domain.jwt;

import com.woochacha.backend.domain.member.service.impl.UserDetailsServiceImpl;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

/**
 * JWT 토큰을 생성하고 유효성을 검증하는 컴포넌트 클래스 JWT 는 여러 암호화 알고리즘을 제공하고 알고리즘과 비밀키를 가지고 토큰을 생성
 * <p>
 * claim 정보에는 토큰에 부가적으로 정보를 추가할 수 있음 claim 정보에 회원을 구분할 수 있는 값을 세팅하였다가 토큰이 들어오면 해당 값으로 회원을 구분하여 리소스
 * 제공
 * <p>
 * JWT 토큰에 expire time을 설정할 수 있음
 */

@Component
public class JwtTokenProvider {

    private final Logger LOGGER = LoggerFactory.getLogger(JwtTokenProvider.class);

    private final UserDetailsServiceImpl userDetailsService; // Spring Security 에서 제공하는 서비스 레이어

    private final String SECRET_KEY;
    private final long TOKEN_VALID_MILLISECOND;

    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey,
                            @Value("${jwt.token-validity-in-seconds}") long tokenValidityInSeconds,
                            UserDetailsServiceImpl userDetailsService) {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes(StandardCharsets.UTF_8));
        this.SECRET_KEY = secretKey;
        this.TOKEN_VALID_MILLISECOND = tokenValidityInSeconds * 24 * 1000L; // 1시간 토큰 유효인데 우선 테스트로 24시간 설정
        this.userDetailsService = userDetailsService;
    }

    // access token 생성
    public String createToken(Authentication authentication) {
        Claims claims = Jwts.claims().setSubject(authentication.getName()); // JWT payload 에 저장되는 정보단위
        claims.put("roles", authentication.getAuthorities()); // 정보는 key / value 쌍으로 저장된다.

        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + TOKEN_VALID_MILLISECOND)) // 토큰 만료 시
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)  // 사용할 암호화 알고리즘과
                // signature 에 들어갈 secret값 세팅
                .compact();
    }

    public UserDetails getUserDetails(String token) {
        // 토큰 복호화
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();

        return userDetailsService.loadUserByUsername(claims.getSubject());
    }

    // JWT 토큰으로 인증 정보 조회
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = getUserDetails(token);
        return new UsernamePasswordAuthenticationToken(userDetails, "",
                userDetails.getAuthorities());
    }

    public String getUserName(String token) {
        UserDetails userDetails = getUserDetails(token);
        return userDetails.getUsername();
    }

    // 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
            return true;
        } catch (SecurityException e) {
            LOGGER.info("기존 서명이 확인되지 않는 JWT 토큰입니다.");
        } catch (MalformedJwtException e) {
            LOGGER.info("올바르게 구성되지 않은 JWT 토큰입니다.");
        } catch (ExpiredJwtException e) {
            LOGGER.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            LOGGER.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            LOGGER.info("JWT 토큰이 잘못되었습니다.");
        } catch (SignatureException e) {
            LOGGER.info("JWT 토큰의 형식이 잘못되었습니다.");
        }
        return false;
    }
}