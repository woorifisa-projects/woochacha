package com.woochacha.backend.domain.member.service.impl;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.woochacha.backend.domain.member.entity.Member;
import com.woochacha.backend.domain.member.entity.QMember;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

// 인증에 필요한 UserDetailsService interface의 loadUserByUsername 메서드를 구현하는 클래스로
// loadUserByUsername 메서드를 통해 Database에 접근하여 사용자 정보를 가지고 온다.
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserDetailsServiceImpl implements UserDetailsService {
    private final JPAQueryFactory queryFactory;

    private final QMember m = QMember.member;
    // 유효한 사용자인지 확인
    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        Optional<Member> memberOptional = Optional.ofNullable(queryFactory
                .selectFrom(m)
                .where(m.email.eq(email), m.id.eq(JPAExpressions.select(m.id.max()).from(m)))
                .fetchOne());

        if(memberOptional.isEmpty())
            throw new BadCredentialsException("사용자 없음");

        return memberOptional.get();
    }
}
