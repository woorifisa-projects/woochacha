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
    @Column(name = "heated_seat")
    private Byte heatedSeat;

    @NotNull
    @Column(name = "smart_key")
    private Byte smartKey;

    @NotNull
    @Column(name = "blackbox")
    private Byte blackbox;

    @NotNull
    @Column(name = "navigation")
    private Byte navigation;

    @NotNull
    @Column(name = "airbag")
    private Byte airbag;

    @NotNull
    @Column(name = "sunroof")
    private Byte sunroof;

    @NotNull
    @Column(name = "high_pass")
    private Byte highPass;

    @NotNull
    @Column(name = "rearview_camera")
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
