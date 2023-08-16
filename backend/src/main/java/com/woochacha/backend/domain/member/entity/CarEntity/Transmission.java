package com.woochacha.backend.domain.member.entity.CarEntity;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
// 차량 변속기 카테고리 정보 엔티티
public class Transmission {
    @Id @GeneratedValue
    @Column(name = "transmission_id")
    private int id;

    private String name;
}
