package com.woochacha.backend.domain.member.entity;

import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Table(name = "car_option")
// 차량의 옵션 정보를 저장하는 엔티티
public class CarOption {
    @Id @GeneratedValue
    @Column(name = "car_option_id")
    private int id;

    @OneToOne
    @JoinColumn(name = "car_num")
    private CarDetail carDetail;

    @NotNull
    private Boolean heated_seat;
    @NotNull
    private Boolean smart_key;
    @NotNull
    private Boolean blackbox;
    @NotNull
    private Boolean navigation;
    @NotNull
    private Boolean airbag;
    @NotNull
    private Boolean sunroof;
    @NotNull
    private Boolean high_pass;
    @NotNull
    private Boolean rearview_camera;
}
