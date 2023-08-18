package com.woochacha.backend.domain.member.entity.carhistory;

import lombok.Getter;

import javax.persistence.*;
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
    private AccidentTypeList type;
}
