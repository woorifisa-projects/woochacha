package com.woochacha.backend.domain.member.entity.CarEntity;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
// 차량 브랜드/제조사 카테고리 정보 저장 엔티티
public class Model {
    @Id @GeneratedValue
    @Column(name = "modle_id")
    private int id;

    private String name;
}
