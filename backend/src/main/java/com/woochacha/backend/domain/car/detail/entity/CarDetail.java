package com.woochacha.backend.domain.car.detail.entity;


import com.woochacha.backend.domain.car.type.entity.*;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Table(name = "car_detail")
// 차량 상세정보 데이터 저장 엔티티
public class CarDetail {
    @Id
    @Column(name = "car_num", length = 50) // 컬럼 이름과 길이를 지정합니다.
    private String carNum;

    @NotNull
    private String owner;

    @NotNull
    private String phone;

    @NotNull
    private Integer distance;

    @NotNull
    private short year;

    @NotNull
    private short capacity;

    @OneToOne
    @JoinColumn(name = "type_id")
    private Type type;

    @OneToOne
    @JoinColumn(name = "model_id")
    private Model model;

    @OneToOne
    @JoinColumn(name = "fuel_id")
    private Fuel fuel;

    @OneToOne
    @JoinColumn(name = "color_id")
    private Color color;

    @OneToOne
    @JoinColumn(name = "transmission_id")
    private Transmission transmission;

    @OneToOne
    @JoinColumn(name = "car_name")
    private CarName carName;

}
