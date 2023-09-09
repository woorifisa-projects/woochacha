package com.woochacha.backend.domain.status.entity;

import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Table(name = "car_status")
// 게시글 상태 정보 엔티티
public class CarStatus {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private short id;

    @Enumerated(value = EnumType.STRING)
    @NotNull
    @Column(name = "status")
    private CarStatusList status;
}
