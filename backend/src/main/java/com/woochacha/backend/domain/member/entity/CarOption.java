package com.woochacha.backend.domain.member.entity;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
// 차량의 옵션 정보를 저장하는 엔티티
public class CarOption {
    @Id @GeneratedValue
    @Column(name = "car_option_id")
    private int id;

    private Boolean heated_seat;
    private Boolean smart_key;
    private Boolean blackbox;
    private Boolean navigation;
    private Boolean airbag;
    private Boolean sunroof;
    private Boolean high_pass;
    private Boolean rearview_camera;
}
