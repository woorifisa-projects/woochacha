package com.woochacha.backend.domain.car.type.entity;

import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Table(name = "fuel")
// 차량 연료 카테고리 정보 저장 엔티티
public class Fuel {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column(name = "name")
    private String name;
}
