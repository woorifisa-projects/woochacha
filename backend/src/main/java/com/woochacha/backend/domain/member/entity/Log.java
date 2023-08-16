package com.woochacha.backend.domain.member.entity;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
// 사용자 로그 정보 관리 엔티티
public class Log {
    @Id @GeneratedValue
    @Column(name = "log_id")
    private Long id;

    private String email;

    private String nickname;

    private LocalDateTime date;

    private String type;

    private String etc;
}
