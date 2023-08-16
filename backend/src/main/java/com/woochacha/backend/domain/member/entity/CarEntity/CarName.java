package com.woochacha.backend.domain.member.entity.CarEntity;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
// 차량 이름 정보 저장 엔티티
public class CarName {
    @Id @GeneratedValue
    @Column(name = "car_name_id")
    private Long id;

    private String name;


}
