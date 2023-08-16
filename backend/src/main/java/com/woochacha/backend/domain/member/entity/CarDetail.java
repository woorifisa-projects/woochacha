package com.woochacha.backend.domain.member.entity;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
// 차량 상세정보 데이터 저장 엔티티
public class CarDetail {
    @Id
    @Column(name = "car_num", length = 50) // 컬럼 이름과 길이를 지정합니다.
    private String car_num;

    private String owner;

    private String phone;

    private int distance;

    private short year;

    private short capacity;
}
