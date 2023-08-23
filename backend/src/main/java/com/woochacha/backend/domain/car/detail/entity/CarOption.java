package com.woochacha.backend.domain.car.detail.entity;

import lombok.Getter;

import javax.persistence.*;
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
    private Byte heatedSeat;

    @NotNull
    private Byte smartKey;

    @NotNull
    private Byte blackbox;

    @NotNull
    private Byte navigation;

    @NotNull
    private Byte airbag;

    @NotNull
    private Byte sunroof;

    @NotNull
    private Byte highPass;

    @NotNull
    private Byte rearviewCamera;
}
