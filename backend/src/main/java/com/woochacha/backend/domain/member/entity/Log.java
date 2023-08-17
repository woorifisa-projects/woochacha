package com.woochacha.backend.domain.member.entity;

import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "log")
// 사용자 로그 정보 관리 엔티티
public class Log {
    @Id @GeneratedValue
    @Column(name = "log_id")
    private Long id;
    @NotNull
    private String email;
    @NotNull
    private String username;
    @NotNull
    private LocalDateTime date;
    @NotNull
    private String type;
    @NotNull
    private String etc;
}
