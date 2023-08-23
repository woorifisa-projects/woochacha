package com.woochacha.backend.domain.car.detail.entity;

import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Table(name = "car_option")
// 차량의 옵션 정보를 저장하는 엔티티
public class CarOption {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "car_num")
    private CarDetail carDetail;

    @NotNull
    private Boolean heatedSeat;

    @NotNull
    private Boolean smartKey;

    @NotNull
    private Boolean blackbox;

    @NotNull
    private Boolean navigation;

    @NotNull
    private Boolean airbag;

    @NotNull
    private Boolean sunroof;

    @NotNull
    private Boolean highPass;

    @NotNull
    private Boolean rearviewCamera;
}
