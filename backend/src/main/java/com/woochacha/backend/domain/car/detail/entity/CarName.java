package com.woochacha.backend.domain.car.detail.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "car_name")
// 차량 이름 정보 저장 엔티티
public class CarName {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    public CarName(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
