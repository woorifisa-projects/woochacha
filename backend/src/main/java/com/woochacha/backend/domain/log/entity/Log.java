package com.woochacha.backend.domain.log.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@DynamicInsert
@Table(name = "log")
// 사용자 로그 정보 관리 엔티티
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "email")
    private String email;

    @NotNull
    @Column(name = "name")
    private String name;

    @CreationTimestamp
    @Column(name = "date")
    private LocalDateTime date;

    @NotNull
    @Column(name = "type")
    private String type;

    @Column(name = "etc")
    private String etc;

    public Log(Long id, String email, String name, String type, String etc) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.type = type;
        this.etc = etc;
    }
}
