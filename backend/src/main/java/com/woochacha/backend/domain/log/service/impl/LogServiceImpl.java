package com.woochacha.backend.domain.log.service.impl;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.woochacha.backend.domain.log.entity.Log;
import com.woochacha.backend.domain.log.repository.LogRepository;
import com.woochacha.backend.domain.log.service.LogService;
import com.woochacha.backend.domain.member.entity.Member;
import com.woochacha.backend.domain.member.entity.QMember;
import com.woochacha.backend.domain.member.repository.MemberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class LogServiceImpl implements LogService {

    private final MemberRepository memberRepository;

    private final LogRepository logRepository;

    private final JPAQueryFactory queryFactory;

    private final QMember m = QMember.member;

    private final Logger logger = LoggerFactory.getLogger(LogService.class);

    public LogServiceImpl(MemberRepository memberRepository, LogRepository logRepository, JPAQueryFactory queryFactory) {
        this.memberRepository = memberRepository;
        this.logRepository = logRepository;
        this.queryFactory = queryFactory;
    }


    @Override
    @Transactional
    public void savedMemberLogWithType(Long memberId, String type) {
        try {
            Member member = findMember(memberId);

            // 로그아웃 로그 저장
            Log log = Log.builder()
                    .email(member.getEmail())
                    .name(member.getUsername())
                    .type(type)
                    .build();

            logger.info("##########################");
            logger.info(log.toString());

            logRepository.save(log);
        } catch (Exception e) {
            logger.error(e.toString());
        }
    }

    @Override
    @Transactional
    public void savedMemberLogWithTypeAndEtc(Long memberId, String type, String url) {
        try {
            Member member = findMember(memberId);

            Log log = Log.builder()
                    .email(member.getEmail())
                    .name(member.getName())
                    .type(type)
                    .etc(url)
                    .build();

            logRepository.save(log);
        } catch (Exception e) {
            logger.error(e.toString());
        }
    }


    private Member findMember(Long memberId) {
        Member member = null;
        Optional<Member> memberOptional = Optional.ofNullable(queryFactory
                .selectFrom(m)
                .where(m.id.eq(memberId), m.id.eq(JPAExpressions.select(m.id.max()).from(m)))
                .fetchOne());

        if(memberOptional.isPresent())
            member = memberOptional.get();
        return member;
    }
}
