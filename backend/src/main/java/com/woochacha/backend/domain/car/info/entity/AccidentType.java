package com.woochacha.backend.domain.car.info.entity;

import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Table(name = "accident_type")
// 차량 사고 이력 종류 저장 엔티티
public class AccidentType {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private short id;

    @Enumerated(value = EnumType.STRING)
    @NotNull
    @Column(name = "type")
    private AccidentTypeList type;
}
