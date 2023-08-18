package com.woochacha.backend.domain.log.entity;

import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Getter
@DynamicInsert
@Table(name = "log")
// 사용자 로그 정보 관리 엔티티
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String email;

    @NotBlank
    private String username;

    @CreationTimestamp
    @NotBlank
    private LocalDateTime date;

    @NotBlank
    private String type;

    private String etc;
}
