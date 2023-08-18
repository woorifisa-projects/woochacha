package com.woochacha.backend.domain.car.detail.entity;

import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Getter
@Table(name = "car_name")
// 차량 이름 정보 저장 엔티티
public class CarName {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;
}
