package com.woochacha.backend.domain.car.detail.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    public CarOption(int id, CarDetail carDetail, Byte heatedSeat, Byte smartKey, Byte blackbox, Byte navigation, Byte airbag, Byte sunroof, Byte highPass, Byte rearviewCamera) {
        this.id = id;
        this.carDetail = carDetail;
        this.heatedSeat = heatedSeat;
        this.smartKey = smartKey;
        this.blackbox = blackbox;
        this.navigation = navigation;
        this.airbag = airbag;
        this.sunroof = sunroof;
        this.highPass = highPass;
        this.rearviewCamera = rearviewCamera;
    }
}
