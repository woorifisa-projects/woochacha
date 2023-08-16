package com.woochacha.backend.domain.member.entity.CarEntity;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
// 차종 카테고리 정보 저장 엔티티
public class Type {
    @Id @GeneratedValue
    @Column(name = "type_id")
    private int id;

    private String name;
}
