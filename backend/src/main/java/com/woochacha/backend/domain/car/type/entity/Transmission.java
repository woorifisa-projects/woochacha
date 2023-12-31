package com.woochacha.backend.domain.car.type.entity;

import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Table(name = "transmission")
// 차량 변속기 카테고리 정보 엔티티
public class Transmission {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(value = EnumType.STRING)
    @NotNull
    @Column(name = "name")
    private TransmissionList name;
}
