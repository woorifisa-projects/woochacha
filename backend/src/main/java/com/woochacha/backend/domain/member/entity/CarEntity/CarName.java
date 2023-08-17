package com.woochacha.backend.domain.member.entity.CarEntity;

import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Table(name = "car_name")
// 차량 이름 정보 저장 엔티티
public class CarName {
    @Id @GeneratedValue
    @Column(name = "car_name_id")
    private Long id;

    @NotNull
    private String name;
}
