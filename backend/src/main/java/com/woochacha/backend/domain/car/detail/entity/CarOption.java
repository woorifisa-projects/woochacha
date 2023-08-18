package com.woochacha.backend.domain.car.detail.entity;

import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

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

    @NotBlank
    private Boolean heatedSeat;

    @NotBlank
    private Boolean smartKey;

    @NotBlank
    private Boolean blackbox;

    @NotBlank
    private Boolean navigation;

    @NotBlank
    private Boolean airbag;

    @NotBlank
    private Boolean sunroof;

    @NotBlank
    private Boolean highPass;

    @NotBlank
    private Boolean rearviewCamera;
}
