package com.woochacha.backend.domain.member.entity;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
// 차량 사고 이력 종류 저장 엔티티
public class AccidentType {
    @Id @GeneratedValue
    @Column(name = "accident_type")
    private short id;

    private String type;
}
