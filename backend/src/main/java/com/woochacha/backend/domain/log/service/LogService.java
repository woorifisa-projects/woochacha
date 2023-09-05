package com.woochacha.backend.domain.log.service;

public interface LogService {
    void savedMemberLogWithType(Long memberId, String type);
    void savedMemberLogWithTypeAndEtc(Long memberId, String type, String etc);
}
