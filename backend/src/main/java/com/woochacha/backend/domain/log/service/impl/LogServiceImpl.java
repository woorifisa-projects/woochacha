package com.woochacha.backend.domain.log.service.impl;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.woochacha.backend.domain.log.entity.Log;
import com.woochacha.backend.domain.log.repository.LogRepository;
import com.woochacha.backend.domain.log.service.LogService;
import com.woochacha.backend.domain.member.entity.Member;
import com.woochacha.backend.domain.member.entity.QMember;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class LogServiceImpl implements LogService {

    private final LogRepository logRepository;

    private final JPAQueryFactory queryFactory;

    private final QMember m = QMember.member;

    private final Logger logger = LoggerFactory.getLogger(LogService.class);

    public LogServiceImpl(LogRepository logRepository, JPAQueryFactory queryFactory) {
        this.logRepository = logRepository;
        this.queryFactory = queryFactory;
    }

    @Override
    @Transactional
    public void savedMemberLogWithType(Long memberId, String type) {
        Member member = findMember(memberId);

        Log log = Log.builder()
                .email(member.getEmail())
                .name(member.getName())
                .type(type)
                .build();

        logRepository.save(log);
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
        return queryFactory
                .selectFrom(m)
                .where(m.id.eq(JPAExpressions.select(m.id.max()).from(m).where(m.id.eq(memberId))))
                .fetchOne();
    }
}
