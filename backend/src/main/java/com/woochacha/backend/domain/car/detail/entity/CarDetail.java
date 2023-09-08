package com.woochacha.backend.domain.car.detail.entity;


import com.woochacha.backend.domain.car.type.entity.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "car_detail")
// 차량 상세정보 데이터 저장 엔티티
public class CarDetail {
    @Id
    @Column(name = "car_num", length = 50) // 컬럼 이름과 길이를 지정합니다.
    private String carNum;

    @NotNull
    @Column(name = "owner")
    private String owner;

    @NotNull
    @Column(name = "phone")
    private String phone;

    @NotNull
    @Column(name = "distance")
    private Integer distance;

    @NotNull
    @Column(name = "year")
    private short year;

    @NotNull
    @Column(name = "capacity")
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

    public CarDetail(String carNum, String owner, String phone, Integer distance, short year, short capacity, Type type, Model model, Fuel fuel, Color color, Transmission transmission, CarName carName) {
        this.carNum = carNum;
        this.owner = owner;
        this.phone = phone;
        this.distance = distance;
        this.year = year;
        this.capacity = capacity;
        this.type = type;
        this.model = model;
        this.fuel = fuel;
        this.color = color;
        this.transmission = transmission;
        this.carName = carName;
    }
}
